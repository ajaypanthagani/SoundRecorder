package soundrecorder.interfaces;

public interface Provider<T> {
    public void subscribe(Subscriber<T> subscriber);
    public void unsubscribe(Subscriber<T> subscriber);
    public void notifyAllSubscribers(T data);
}
