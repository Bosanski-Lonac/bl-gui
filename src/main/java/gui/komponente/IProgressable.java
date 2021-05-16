package gui.komponente;

public interface IProgressable {
    void addProgress(double progress);
    void setProgress(double progress);
    void finish(boolean success);
}
