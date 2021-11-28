package co.tiagoaguiar.recyclermasterjava;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import co.tiagoaguiar.recyclermasterjava.model.Pessoa;

/**
 * Julho, 03 2019
 *
 * @author suporte@moonjava.com.br (Tiago Aguiar).
 */
class PessoaAdapter extends RecyclerView.Adapter<PessoaAdapter.EmailViewHolder> {

  private EmailAdapterListener listener;
  private final List<Pessoa> pessoas;
  final SparseBooleanArray selectedItems = new SparseBooleanArray();
  private int currentSelectedPos;

  PessoaAdapter(List<Pessoa> pessoas) {
    this.pessoas = pessoas;
  }

  public List<Pessoa> getEmails() {
    return pessoas;
  }

  public void setListener(EmailAdapterListener listener) {
    this.listener = listener;
  }

  @NonNull
  @Override
  public EmailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(
            R.layout.item, parent, false);
    return new EmailViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull EmailViewHolder holder, final int position) {
    Pessoa pessoa = pessoas.get(position);
    holder.bind(pessoa);

    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (selectedItems.size() > 0 && listener != null)
          listener.onItemClick(position);
      }
    });

    holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
      @Override
      public boolean onLongClick(View view) {
        if (listener != null)
          listener.onItemLongClick(position);
        return true;
      }
    });

    if (currentSelectedPos == position) currentSelectedPos = -1;
  }

  @Override
  public int getItemCount() {
    return pessoas.size();
  }

  void deleteEmails() {
    List<Pessoa> pessoas = new ArrayList<>();
    for (Pessoa pessoa : this.pessoas) {
      if (pessoa.isSelected())
        pessoas.add(pessoa);
    }

    this.pessoas.removeAll(pessoas);
    notifyDataSetChanged();
    currentSelectedPos = -1;
  }

  void toggleSelection(int position) {
    currentSelectedPos = position;
    if (selectedItems.get(position)) {
      selectedItems.delete(position);
      pessoas.get(position).setSelected(false);
    } else {
      selectedItems.put(position, true);
      pessoas.get(position).setSelected(true);
    }
    notifyItemChanged(position);
  }

  class EmailViewHolder extends RecyclerView.ViewHolder {

    TextView txtUser;
    TextView txtIcon;
    TextView txtSubject;
    TextView txtPreview;
    TextView txtDate;
    ImageView imgStar;

    EmailViewHolder(@NonNull View itemView) {
      super(itemView);
      txtUser = itemView.findViewById(R.id.txt_user);
      txtIcon = itemView.findViewById(R.id.txt_icon);
      txtSubject = itemView.findViewById(R.id.txt_subject);
      txtPreview = itemView.findViewById(R.id.txt_preview);
      txtDate = itemView.findViewById(R.id.txt_date);
      imgStar = itemView.findViewById(R.id.img_star);
    }

    void bind(Pessoa pessoa) {
      int hash = pessoa.getUser().hashCode();
      txtIcon.setText(String.valueOf(pessoa.getUser().charAt(0)));
      txtIcon.setBackground(oval(Color.rgb(hash, hash / 2, 0), txtIcon));
      txtUser.setText(pessoa.getUser());
      txtSubject.setText(pessoa.getSubject());
      txtPreview.setText(pessoa.getPreview());
      txtDate.setText(pessoa.getDate());

      txtUser.setTypeface(Typeface.DEFAULT, pessoa.isUnread() ? Typeface.BOLD : Typeface.NORMAL);
      txtSubject.setTypeface(Typeface.DEFAULT, pessoa.isUnread() ? Typeface.BOLD : Typeface.NORMAL);
      txtDate.setTypeface(Typeface.DEFAULT, pessoa.isUnread() ? Typeface.BOLD : Typeface.NORMAL);

      imgStar.setImageResource(pessoa.isStared()
              ? R.drawable.ic_star_black_24dp
              : R.drawable.ic_star_border_black_24dp);

      if (pessoa.isSelected()) {
        txtIcon.setBackground(oval(Color.rgb(26, 115, 233), txtIcon));
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setCornerRadius(32f);
        gradientDrawable.setColor(Color.rgb(232, 240, 253));
        itemView.setBackground(gradientDrawable);
      } else {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setCornerRadius(32f);
        gradientDrawable.setColor(Color.WHITE);
        itemView.setBackground(gradientDrawable);
      }

      // animation
      if (selectedItems.size() > 0)
        animate(txtIcon, pessoa);
    }

    private void animate(final TextView view, final Pessoa pessoa) {
      ObjectAnimator oa1 = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0f);
      final ObjectAnimator oa2 = ObjectAnimator.ofFloat(view, "scaleX", 0f, 1f);
      oa1.setInterpolator(new DecelerateInterpolator());
      oa2.setInterpolator(new AccelerateDecelerateInterpolator());
      oa1.setDuration(200);
      oa2.setDuration(200);

      oa1.addListener(new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
          super.onAnimationEnd(animation);
          if (pessoa.isSelected())
            view.setText("\u2713");
          oa2.start();
        }
      });
      oa1.start();
    }

  }

  private static ShapeDrawable oval(@ColorInt int color, View view) {
    ShapeDrawable shapeDrawable = new ShapeDrawable(new OvalShape());
    shapeDrawable.setIntrinsicHeight(view.getHeight());
    shapeDrawable.setIntrinsicWidth(view.getWidth());
    shapeDrawable.getPaint().setColor(color);
    return shapeDrawable;
  }

  interface EmailAdapterListener {
    void onItemClick(int position);
    void onItemLongClick(int position);
  }

}
