package co.tiagoaguiar.recyclermasterjava.model;

/**
 * Julho, 03 2019
 *
 * @author suporte@moonjava.com.br (Tiago Aguiar).
 */
public class Pessoa {

  private String user;
  private String subject;
  private String preview;
  private String date;
  private boolean stared;
  private boolean unread;
  private boolean selected;

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getPreview() {
    return preview;
  }

  public void setPreview(String preview) {
    this.preview = preview;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public boolean isStared() {
    return stared;
  }

  public void setStared(boolean stared) {
    this.stared = stared;
  }

  public boolean isUnread() {
    return unread;
  }

  public void setUnread(boolean unread) {
    this.unread = unread;
  }

  public boolean isSelected() {
    return selected;
  }

  public void setSelected(boolean selected) {
    this.selected = selected;
  }

  public static class PessoaBuilder {

    private String user;
    private String subject;
    private String preview;
    private String date;
    private boolean stared;
    private boolean unread;
    private boolean selected;

    public PessoaBuilder setUser(String user) {
      this.user = user;
      return this;
    }

    public PessoaBuilder setSubject(String subject) {
      this.subject = subject;
      return this;
    }

    public PessoaBuilder setPreview(String preview) {
      this.preview = preview;
      return this;
    }

    public PessoaBuilder setDate(String date) {
      this.date = date;
      return this;
    }

    public PessoaBuilder setStared(boolean stared) {
      this.stared = stared;
      return this;
    }

    public PessoaBuilder setUnread(boolean unread) {
      this.unread = unread;
      return this;
    }

    public PessoaBuilder setSelected(boolean selected) {
      this.selected = selected;
      return this;
    }

    private PessoaBuilder() {}

    public static PessoaBuilder builder() {
      return new PessoaBuilder();
    }

    public Pessoa build() {
      Pessoa pessoa = new Pessoa();
      pessoa.user = user;
      pessoa.subject = subject;
      pessoa.preview = preview;
      pessoa.date = date;
      pessoa.stared = stared;
      pessoa.unread = unread;
      pessoa.selected = selected;
      return pessoa;
    }

  }

}
