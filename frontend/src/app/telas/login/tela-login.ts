import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-tela-login',
  imports: [FormsModule],
  templateUrl: './tela-login.html',
  styleUrl: './tela-login.scss'
})
export class TelaLogin {
  usuario = '';
  senha = '';
  mensagemErro = '';

  private readonly acessos: Record<string, { senha: string; destino: string; perfil: string }> = {
    admin: { senha: 'admin', destino: '/gestor/dashboard', perfil: 'Gestor' },
    testeadmin: { senha: 'testeadmin', destino: '/gestor/dashboard', perfil: 'Gestor' },
    testepro: { senha: 'testepro', destino: '/profissional/ocorrencias', perfil: 'Profissional' },
    testeaten: { senha: 'testeaten', destino: '/atendente/ocorrencias', perfil: 'Atendente' }
  };

  constructor(private readonly roteador: Router) {}

  entrar(): void {
    const usuarioTratado = this.usuario.trim().toLowerCase();
    const acesso = this.acessos[usuarioTratado];

    if (!acesso || acesso.senha !== this.senha) {
      this.mensagemErro = 'Usuário ou senha inválidos.';
      return;
    }

    localStorage.setItem('perfil', acesso.perfil);
    this.mensagemErro = '';
    this.roteador.navigateByUrl(acesso.destino);
  }
}
