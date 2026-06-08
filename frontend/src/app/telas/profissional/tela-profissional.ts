import { Component } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';

type TelaProfissionalSelecionada = 'ocorrencias' | 'relatorios';

@Component({
  selector: 'app-tela-profissional',
  imports: [RouterLink],
  templateUrl: './tela-profissional.html',
  styleUrl: './tela-profissional.scss'
})
export class TelaProfissional {
  telaAtual: TelaProfissionalSelecionada = 'ocorrencias';
  private readonly telasPermitidas: TelaProfissionalSelecionada[] = ['ocorrencias', 'relatorios'];

  constructor(
    private readonly rota: ActivatedRoute,
    private readonly roteador: Router
  ) {
    this.rota.paramMap.subscribe((parametros) => {
      const tela = parametros.get('tela') as TelaProfissionalSelecionada | null;

      if (tela && this.telasPermitidas.includes(tela)) {
        this.telaAtual = tela;
        return;
      }

      this.roteador.navigateByUrl('/profissional/ocorrencias');
    });
  }

  trocarTela(tela: TelaProfissionalSelecionada): void {
    this.roteador.navigateByUrl(`/profissional/${tela}`);
  }

  sair(): void {
    localStorage.removeItem('perfil');
    this.roteador.navigateByUrl('/');
  }
}
