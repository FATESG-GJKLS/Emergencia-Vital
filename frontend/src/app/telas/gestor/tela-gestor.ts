import { Component } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';

type TelaGestorSelecionada =
  | 'dashboard'
  | 'equipes'
  | 'criar-equipe'
  | 'colaboradores'
  | 'cadastrar-colaborador'
  | 'ambulancias'
  | 'cadastrar-ambulancia'
  | 'ocorrencias';

@Component({
  selector: 'app-tela-gestor',
  imports: [RouterLink],
  templateUrl: './tela-gestor.html',
  styleUrl: './tela-gestor.scss'
})
export class TelaGestor {
  telaAtual: TelaGestorSelecionada = 'dashboard';
  private readonly telasPermitidas: TelaGestorSelecionada[] = [
    'dashboard',
    'equipes',
    'criar-equipe',
    'colaboradores',
    'cadastrar-colaborador',
    'ambulancias',
    'cadastrar-ambulancia',
    'ocorrencias'
  ];

  constructor(
    private readonly rota: ActivatedRoute,
    private readonly roteador: Router
  ) {
    this.rota.paramMap.subscribe((parametros) => {
      const tela = parametros.get('tela') as TelaGestorSelecionada | null;

      if (tela && this.telasPermitidas.includes(tela)) {
        this.telaAtual = tela;
        return;
      }

      this.roteador.navigateByUrl('/gestor/dashboard');
    });
  }

  trocarTela(tela: TelaGestorSelecionada): void {
    this.roteador.navigateByUrl(`/gestor/${tela}`);
  }

  sair(): void {
    localStorage.removeItem('perfil');
    this.roteador.navigateByUrl('/');
  }
}
