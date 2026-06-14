import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { API_BASE_URL } from './api-base';
import { AmbulanciaResponseDTO, DespachoResponseDTO, EquipeResponseDTO, OcorrenciaResponseDTO, RegistroDespachoDTO, RegistroOcorrenciaDTO, RegistroFinalizacaoDespachoDTO } from '../modelos/api';

@Injectable({ providedIn: 'root' })
export class AtendenteService {
  constructor(private readonly http: HttpClient) {}

  listarOcorrencias(): Observable<OcorrenciaResponseDTO[]> {
    return this.http.get<OcorrenciaResponseDTO[]>(`${API_BASE_URL}/atendente/ocorrencias`);
  }

  registrarOcorrencia(dto: RegistroOcorrenciaDTO): Observable<OcorrenciaResponseDTO> {
    return this.http.post<OcorrenciaResponseDTO>(`${API_BASE_URL}/atendente/ocorrencias`, dto);
  }

  listarDespachos(): Observable<DespachoResponseDTO[]> {
    return this.http.get<DespachoResponseDTO[]>(`${API_BASE_URL}/atendente/despachos`);
  }

  listarEquipes(): Observable<EquipeResponseDTO[]> {
    return this.http.get<EquipeResponseDTO[]>(`${API_BASE_URL}/atendente/equipes`);
  }

  listarAmbulancias(): Observable<AmbulanciaResponseDTO[]> {
    return this.http.get<AmbulanciaResponseDTO[]>(`${API_BASE_URL}/atendente/ambulancias`);
  }

  informarChegada(idDespacho: number): Observable<DespachoResponseDTO> {
    return this.http.post<DespachoResponseDTO>(`${API_BASE_URL}/profissional/despachos/${idDespacho}/informar-chegada`, {});
  }

  finalizarDespacho(idDespacho: number, dto: RegistroFinalizacaoDespachoDTO): Observable<DespachoResponseDTO> {
    return this.http.post<DespachoResponseDTO>(`${API_BASE_URL}/profissional/despachos/${idDespacho}/finalizar`, dto);
  }

  enviarDespacho(dto: RegistroDespachoDTO): Observable<DespachoResponseDTO> {
    return this.http.post<DespachoResponseDTO>(`${API_BASE_URL}/atendente/despachos`, dto);
  }

  encerrarOcorrencia(idOcorrencia: number): Observable<OcorrenciaResponseDTO> {
    return this.http.post<OcorrenciaResponseDTO>(`${API_BASE_URL}/atendente/ocorrencias/${idOcorrencia}`, {});
  }
}
