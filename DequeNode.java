/**
 * A simple node for the Deque that
 * can hold a value and a `not` count,
 * used for determining if states should be
 * interpreted as NOT states.
 * 
 * Created by Luke Finlayson, 1557835
 */
public class DequeNode {
    private int index;
    private int notCount;

    public DequeNode(int value, int notCount) {
      this.index = value;
      this.notCount = notCount;
    }

    public int getIndex() {
      return index;
    }

    public int getNotCount() {
      return notCount;
    }

    public boolean isNot() {
      return notCount > 0;
    }

    public void incrementNotCount() {
      notCount++;
    }

    public void decrementNotCount() {
      notCount--;
    }
}
