package soundrecorder.interfaces;

public interface Publisher<T> {
    public void subscribe(Subscriber<T> subscriber);
    public void unsubscribe(Subscriber<T> subscriber);
    public void publish(T data);
}
