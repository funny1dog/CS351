
public interface Subject {
    public void registerObserver(MyObserver o);
    public void removeObserver(MyObserver o);
    public void notifyObservers();
}
