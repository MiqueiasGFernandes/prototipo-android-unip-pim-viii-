package co.tiagoaguiar.recyclermasterjava.model;

import java.util.Arrays;
import java.util.List;

/**
 * Julho, 03 2019
 *
 * @author suporte@moonjava.com.br (Tiago Aguiar).
 */
public class PessoasDados {

  public static List<Pessoa> fakeEmails() {
    return Arrays.asList(

            Pessoa.PessoaBuilder.builder()
                    .setUser("Paulo Martins Costa")
                    .setSubject("Rua Albertino Costa, 1014, Jardim do Lago, Jundiaí - SP")
                    .setPreview("CPF: 260.964.220-20")
                    .setDate("ID: 1")
                    .build(),
            Pessoa.PessoaBuilder.builder()
                    .setUser("Tiago da Silva Bueno")
                    .setSubject("Avenida Reinaldo Janequini, 801, Novo Horizonte, Santo André - SP")
                    .setPreview("CPF: 260.964.220-20")
                    .setDate("ID: 2")
                    .build()
    );


  }

}
