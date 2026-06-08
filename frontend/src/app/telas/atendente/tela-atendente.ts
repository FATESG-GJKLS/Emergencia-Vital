import { Component } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';

type TelaAtendenteSelecionada = 'nova-ocorrencia' | 'ocorrencias';

@Component({
  selector: 'app-tela-atendente',
  imports: [RouterLink],
  templateUrl: './tela-atendente.html',
  styleUrl: './tela-atendente.scss'
})
export class TelaAtendente {
  telaAtual: TelaAtendenteSelecionada = 'ocorrencias';
  private readonly telasPermitidas: TelaAtendenteSelecionada[] = ['nova-ocorrencia', 'ocorrencias'];

  constructor(
    private readonly rota: ActivatedRoute,
    private readonly roteador: Router
  ) {
    this.rota.paramMap.subscribe((parametros) => {
      const tela = parametros.get('tela') as TelaAtendenteSelecionada | null;

      if (tela && this.telasPermitidas.includes(tela)) {
        this.telaAtual = tela;
        return;
      }

      this.roteador.navigateByUrl('/atendente/ocorrencias');
    });
  }

  trocarTela(tela: TelaAtendenteSelecionada): void {
    this.roteador.navigateByUrl(`/atendente/${tela}`);
  }

  sair(): void {
    localStorage.removeItem('perfil');
    this.roteador.navigateByUrl('/');
  }
}
