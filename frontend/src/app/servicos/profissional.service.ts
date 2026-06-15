import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { API_BASE_URL } from './api-base';
import { DespachoResponseDTO, RegistroFinalizacaoDespachoDTO } from '../modelos/api';

@Injectable({ providedIn: 'root' })
export class ProfissionalService {
  constructor(private readonly http: HttpClient) {}

  listarMeusDespachos(): Observable<DespachoResponseDTO[]> {
    return this.http.get<DespachoResponseDTO[]>(`${API_BASE_URL}/profissional/despachos`);
  }

  informarChegada(idDespacho: number): Observable<DespachoResponseDTO> {
    return this.http.post<DespachoResponseDTO>(`${API_BASE_URL}/profissional/despachos/${idDespacho}/informar-chegada`, {});
  }

  finalizarDespacho(idDespacho: number, dto: RegistroFinalizacaoDespachoDTO): Observable<DespachoResponseDTO> {
    return this.http.post<DespachoResponseDTO>(`${API_BASE_URL}/profissional/despachos/${idDespacho}/finalizar`, dto);
  }
}
