package soundrecorder.interfaces;

public interface Subscriber<T> {
    public void onNotify(T data);
}
