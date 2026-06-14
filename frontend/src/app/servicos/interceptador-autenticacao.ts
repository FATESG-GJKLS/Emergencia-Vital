import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';

import { AutenticacaoService } from './autenticacao.service';

export const interceptadorAutenticacao: HttpInterceptorFn = (requisicao, proximo) => {
  const autenticacaoService = inject(AutenticacaoService);
  const token = autenticacaoService.obterToken();

  if (!token) {
    return proximo(requisicao);
  }

  return proximo(
    requisicao.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    })
  );
};
