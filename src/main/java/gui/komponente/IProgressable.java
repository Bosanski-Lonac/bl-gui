package gui.komponente;

public interface IProgressable {
    void addProgress(double progress);
    void setProgress(double progress);
    void fail();
}
