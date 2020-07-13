package bookseller;

import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class BookSellerFacilitatorAgent extends Agent {
  private String searchBookName = null;
  private ACLMessage searchMessage = null;
  private AID[] sellerAgents;
  private AID bestSeller = null;
  private int bestPrice = Integer.MAX_VALUE;
  private MessageTemplate cfpMessageTemplate; // Template to receive CFP replies 
  private MessageTemplate orderMessageTemplate; // Template to receive order reply
  private ACLMessage acceptProposalMessage = null;

  protected void setup () {
    registerService();
    addBehaviour(new SearchHandlerBehaviour());
  }

  protected void takeDown () {
  }

  private void registerService()  {
		DFAgentDescription agentDescription = new DFAgentDescription();
		agentDescription.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("book-search");
		sd.setName("JADE-book-trading");
    agentDescription.addServices(sd);
		try {
      DFService.register(this, agentDescription);
    } catch (FIPAException e) {
      e.printStackTrace();
    }
  }

  public void getSellerAgents () {
    DFAgentDescription template = new DFAgentDescription();
    ServiceDescription serviceDescription = new ServiceDescription();
    serviceDescription.setType("book-selling");
    template.addServices(serviceDescription);
    try {
        DFAgentDescription[]  dfAgentsDescriptions = DFService.search(this, template); 
        AID[] agents = new AID[dfAgentsDescriptions.length];
        for (int i = 0; i < dfAgentsDescriptions.length; i++) {
          agents[i] = dfAgentsDescriptions[i].getName();
        }
        sellerAgents = agents;
        System.out.println("FACILITATOR: Sellers found: ");
        for (AID agent : agents) {
          System.out.println("FACILITATOR: Agent: " + agent.getName());
        }
    } catch (FIPAException e) {
      e.printStackTrace();
    }
  }

  private void sendCallsForProposals () {
    ACLMessage proposalMessage = new ACLMessage(ACLMessage.CFP);

    System.out.println("FACILITATOR: Seller agents " + this.sellerAgents.length);
    for (AID agent : this.sellerAgents) {
      proposalMessage.addReceiver(agent);
      System.out.println("FACILITATOR: Sending CFP to " + agent.getName());
    }
    proposalMessage.setContent(searchBookName);
		proposalMessage.setConversationId("book-selling");
		proposalMessage.setReplyWith("CFP:" + System.currentTimeMillis()); // unique value
    this.send(proposalMessage);
    System.out.println("FACILITATOR: proposals send");
    cfpMessageTemplate = MessageTemplate.and(
      MessageTemplate.MatchConversationId("book-selling"),
      MessageTemplate.MatchInReplyTo(proposalMessage.getReplyWith())
    );
  }

  private void sendProposal () {
    ACLMessage replyMessage = searchMessage.createReply();
    if (bestSeller == null) {
      System.out.println("FACILITATOR: refused ");
      replyMessage.setPerformative(ACLMessage.REFUSE);
      replyMessage.setContent("not-found");
    }
    else {
      System.out.println("FACILITATOR: propose " + this.bestPrice);
      replyMessage.setPerformative(ACLMessage.PROPOSE);
      replyMessage.setContent(String.valueOf(this.bestPrice));
    }
    this.send(replyMessage);
  }

  private void acceptBestSellerProposal() {
    ACLMessage order = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
    order.addReceiver(bestSeller);
    order.setContent(searchBookName);
    order.setConversationId("book-trading");
    order.setReplyWith("ORDER_BOOK:" + System.currentTimeMillis());
    this.send(order);
    orderMessageTemplate = MessageTemplate.and(
      MessageTemplate.MatchConversationId("book-trading"),
      MessageTemplate.MatchInReplyTo(order.getReplyWith())
    );
  }

  class SearchHandlerBehaviour extends Behaviour {
    public void action () {
      this.receiveBookSearch();
    }

    private void receiveBookSearch () {
      MessageTemplate template = MessageTemplate.MatchPerformative(ACLMessage.CFP);
      ACLMessage message = this.myAgent.receive(template);
      if (message == null) {
        this.block();
        return;
      }
      searchBookName = message.getContent(); // upper class searchBookName attribute
      System.out.println("FACILITATOR: Received call for proposal for " + searchBookName);
      searchMessage = message; // upper classs searchMessage attribute
      getSellerAgents(); // upper class method
      sendCallsForProposals(); // upper class method
      System.out.println("FACILITATOR: We are gonna search the next book: " + searchBookName);
      this.myAgent.addBehaviour(new WaitForResponsesBehaviour());
    }

    public boolean done () {
      return searchMessage != null;
    }
  }

  /**
   * Once we send the calls for proposals we start this behaviour to wait for responses
   */
  class WaitForResponsesBehaviour extends Behaviour {
    private int responses = 0;

    public void action () {
      ACLMessage replyMessage = this.myAgent.receive(cfpMessageTemplate);

      if (replyMessage == null) {
        this.block();
        return;
      }

      this.responses++;
      
      if (replyMessage.getPerformative() != ACLMessage.PROPOSE) {
        return;
      }

      String strPrice = replyMessage.getContent();
      int price = Integer.parseInt(strPrice);
      if (price < bestPrice) {
        bestPrice = price;
        bestSeller = replyMessage.getSender();
      }
      System.out.println(
        "FACILITATOR: Getting propose from " + replyMessage.getSender().getName() +
        " - " + strPrice
      );
    }

    public boolean done() {
      boolean finished = responses == sellerAgents.length;
      if (finished) {
        sendProposal();
        this.myAgent.addBehaviour(new WaitProposalResponseBehaviour());
      }
      return finished;
    }
  }

  class WaitProposalResponseBehaviour extends Behaviour {
    public void action () {
      MessageTemplate template = MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL);
      ACLMessage message = this.myAgent.receive(template);
      if (message == null) {
        block();
        return;
      }
      
      acceptProposalMessage = message;
      System.out.println("FACILITATOR: Book sell proposal accepted.");
      acceptBestSellerProposal();
      this.myAgent.addBehaviour(new WaitPurchaseInform());
    }

    public boolean done () {
      return acceptProposalMessage != null;
    }
  }

  class WaitPurchaseInform extends Behaviour {
    boolean informed = false;
    public void action () {
      ACLMessage replyMessage = this.myAgent.receive(orderMessageTemplate);
      if (replyMessage == null ) {
        this.block();
        return;
      }
      
      this.informed = true;
      if (replyMessage.getPerformative() == ACLMessage.INFORM) {
        System.out.println("FACILITATOR: Book sell proposal accepted.");
        ACLMessage proposalReply = acceptProposalMessage.createReply();
        proposalReply.setPerformative(ACLMessage.INFORM);
        System.out.println("FACILITATOR: Sending book sell inform.");
        this.myAgent.send(proposalReply);
        System.out.println("FACILITATOR: Book sell inform sent.");
      } else {
        System.out.println("FACILITATOR: Book sell proposal FAILED.");
        ACLMessage proposalReply = acceptProposalMessage.createReply();
        proposalReply.setPerformative(ACLMessage.FAILURE);
        System.out.println("FACILITATOR: Sending book sell FAILURE.");
        this.myAgent.send(proposalReply);
        System.out.println("FACILITATOR: Book sell inform sent.");
      }
    }

    public boolean done () {
      return this.informed;
    }
  }
}
