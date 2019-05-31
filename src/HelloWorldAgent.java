package bookseller;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;

public class HelloWorldAgent extends Agent {
  public int timer = 3;
  protected void setup () {
    this.addBehaviour(new HelloBehaviour());
  }

  class HelloBehaviour extends Behaviour {
    public void action () {
      System.out.println("Hello world");
    }
    public boolean done() {
      timer--;
      return timer == 0;
    }
  }
}
