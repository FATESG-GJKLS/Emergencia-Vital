import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { API_BASE_URL } from './api-base';
import { AutenticacaoPayload, PerfilAplicacao, TokenResponseDTO, perfilAplicacaoDoFuncionario } from '../modelos/api';

const CHAVE_TOKEN = 'ev.token';
const CHAVE_TIPO = 'ev.tipoFuncionario';
const CHAVE_PERFIL = 'ev.perfil';

@Injectable({ providedIn: 'root' })
export class AutenticacaoService {
  constructor(private readonly http: HttpClient) {}

  autenticar(dados: AutenticacaoPayload): Observable<TokenResponseDTO> {
    return this.http.post<TokenResponseDTO>(`${API_BASE_URL}/login`, dados);
  }

  salvarSessao(resposta: TokenResponseDTO): PerfilAplicacao {
    const perfil = perfilAplicacaoDoFuncionario(resposta.tipoFuncionario);

    localStorage.setItem(CHAVE_TOKEN, resposta.token);
    localStorage.setItem(CHAVE_TIPO, resposta.tipoFuncionario);
    localStorage.setItem(CHAVE_PERFIL, perfil);

    return perfil;
  }

  obterToken(): string | null {
    return localStorage.getItem(CHAVE_TOKEN);
  }

  obterPerfil(): PerfilAplicacao | null {
    const perfil = localStorage.getItem(CHAVE_PERFIL);
    if (perfil === 'GESTOR' || perfil === 'ATENDENTE' || perfil === 'PROFISSIONAL_DA_SAUDE') {
      return perfil;
    }

    const tipoFuncionario = localStorage.getItem(CHAVE_TIPO) as TokenResponseDTO['tipoFuncionario'] | null;
    return tipoFuncionario ? perfilAplicacaoDoFuncionario(tipoFuncionario) : null;
  }

  possuiSessao(): boolean {
    return !!this.obterToken();
  }

  sair(): void {
    localStorage.removeItem(CHAVE_TOKEN);
    localStorage.removeItem(CHAVE_TIPO);
    localStorage.removeItem(CHAVE_PERFIL);
  }
}
