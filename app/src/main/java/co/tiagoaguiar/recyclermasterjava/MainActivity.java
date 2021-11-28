package co.tiagoaguiar.recyclermasterjava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mooveit.library.Fakeit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import co.tiagoaguiar.recyclermasterjava.model.Pessoa;
import co.tiagoaguiar.recyclermasterjava.model.PessoasDados;

public class MainActivity extends AppCompatActivity {

  private PessoaAdapter pessoaAdapter;
  private ActionMode actionMode;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Fakeit.init();
    setContentView(R.layout.activity_main);

    pessoaAdapter = new PessoaAdapter(new ArrayList<>(PessoasDados.fakeEmails()));

    final RecyclerView rv = findViewById(R.id.recycler_view_main);
    rv.setAdapter(pessoaAdapter);

    findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        addEmail();
        rv.scrollToPosition(0);
      }
    });

    ItemTouchHelper helper = new ItemTouchHelper(
            // below statement: used at move and sort
            // new ItemTouchHandler(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                    //ItemTouchHelper.LEFT)
    new ItemTouchHandler(0,
            ItemTouchHelper.LEFT)
    );

    helper.attachToRecyclerView(rv);

    pessoaAdapter.setListener(new PessoaAdapter.EmailAdapterListener() {
      @Override
      public void onItemClick(int position) {
        enableActionMode(position);
      }

      @Override
      public void onItemLongClick(int position) {
        enableActionMode(position);
      }
    });
  }

  private void enableActionMode(int position) {
    if (actionMode == null)
      actionMode = startSupportActionMode(new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
          mode.getMenuInflater().inflate(R.menu.menu_delete, menu);
          return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
          return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
          if (item.getItemId() == R.id.action_delete) {
            pessoaAdapter.deleteEmails();
            mode.finish();
            return true;
          }
          return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
          pessoaAdapter.selectedItems.clear();
          List<Pessoa> pessoas = pessoaAdapter.getEmails();
          for (Pessoa pessoa : pessoas) {
            if (pessoa.isSelected())
              pessoa.setSelected(false);
          }

          pessoaAdapter.notifyDataSetChanged();
          actionMode = null;
        }
      });

    pessoaAdapter.toggleSelection(position);
    final int size = pessoaAdapter.selectedItems.size();
    if (size == 0) {
      actionMode.finish();
    } else {
      actionMode.setTitle(size + "");
      actionMode.invalidate();
    }

  }

  private class ItemTouchHandler extends ItemTouchHelper.SimpleCallback {

    public ItemTouchHandler(int dragDirs, int swipeDirs) {
      super(dragDirs, swipeDirs);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
      int from = viewHolder.getAdapterPosition();
      int to = target.getAdapterPosition();

      Collections.swap(pessoaAdapter.getEmails(), from, to);
      pessoaAdapter.notifyItemMoved(from, to);

      return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
      pessoaAdapter.getEmails().remove(viewHolder.getAdapterPosition());
      pessoaAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
    }
  }

  private void addEmail() {
    try {
      Date sdf = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR")).parse(
              Fakeit.dateTime().dateFormatter()
      );

      StringBuilder preview = new StringBuilder();
      for (int i = 0; i < 10; i++) {
        preview.append(Fakeit.lorem().words());
        preview.append(" ");
      }

      pessoaAdapter.getEmails().add(0, Pessoa.PessoaBuilder.builder()
              .setStared(false)
              .setUnread(true)
              .setUser(Fakeit.name().firstName())
              .setSubject(Fakeit.company().name())
              .setDate(new SimpleDateFormat("d MMM",
                      new Locale("pt", "BR")).format(sdf))
              .setPreview(preview.toString())
              .build()
      );

    } catch (ParseException e) {
    }

    pessoaAdapter.notifyItemInserted(0);

  }



}
