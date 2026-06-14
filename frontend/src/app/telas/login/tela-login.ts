import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

import { rotaDoPerfilAplicacao } from '../../modelos/api';
import { AutenticacaoService } from '../../servicos/autenticacao.service';

@Component({
  selector: 'app-tela-login',
  imports: [FormsModule],
  templateUrl: './tela-login.html',
  styleUrl: './tela-login.scss'
})
export class TelaLogin {
  cpf = '';
  senha = '';
  mensagemErro = '';

  constructor(
    private readonly roteador: Router,
    private readonly autenticacaoService: AutenticacaoService
  ) {}

  entrar(): void {
    this.autenticacaoService.autenticar({ CPF: this.cpf.trim(), senha: this.senha.trim() }).subscribe({
      next: (resposta) => {
        const perfil = this.autenticacaoService.salvarSessao(resposta);
        this.mensagemErro = '';
        this.roteador.navigateByUrl(rotaDoPerfilAplicacao(perfil));
      },
      error: () => {
        this.mensagemErro = 'CPF ou senha inválidos.';
      }
    });
  }

  formatarCPF(event: any): void {
    let v = event.target.value.replace(/\D/g, '');
    if (v.length > 11) v = v.substring(0, 11);
    if (v.length > 9) v = v.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/, "$1.$2.$3-$4");
    else if (v.length > 6) v = v.replace(/(\d{3})(\d{3})(\d{3})/, "$1.$2.$3");
    else if (v.length > 3) v = v.replace(/(\d{3})(\d{3})/, "$1.$2");
    this.cpf = v;
    event.target.value = v;
  }
}
