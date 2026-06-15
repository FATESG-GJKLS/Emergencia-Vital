import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { API_BASE_URL } from './api-base';
import { AmbulanciaResponseDTO, RegistroEquipeDTO, DespachoResponseDTO, EquipeResponseDTO, FuncionarioResponseDTO, OcorrenciaResponseDTO, RegistroFuncionarioDTO, RegistroAmbulanciaDTO, EdicaoFuncionarioDTO, EdicaoAmbulanciaDTO } from '../modelos/api';

@Injectable({ providedIn: 'root' })
export class GestorService {
  constructor(private readonly http: HttpClient) {}

  cadastrarFuncionario(dto: RegistroFuncionarioDTO): Observable<FuncionarioResponseDTO> {
    return this.http.post<FuncionarioResponseDTO>(`${API_BASE_URL}/gestor/funcionarios`, dto);
  }

  cadastrarAmbulancia(dto: RegistroAmbulanciaDTO): Observable<AmbulanciaResponseDTO> {
    return this.http.post<AmbulanciaResponseDTO>(`${API_BASE_URL}/gestor/ambulancias`, dto);
  }

  listarFuncionarios(): Observable<FuncionarioResponseDTO[]> {
    return this.http.get<FuncionarioResponseDTO[]>(`${API_BASE_URL}/gestor/funcionarios`);
  }

  listarAmbulancias(): Observable<AmbulanciaResponseDTO[]> {
    return this.http.get<AmbulanciaResponseDTO[]>(`${API_BASE_URL}/gestor/ambulancias`);
  }

  listarEquipes(): Observable<EquipeResponseDTO[]> {
    return this.http.get<EquipeResponseDTO[]>(`${API_BASE_URL}/gestor/equipes`);
  }

  listarOcorrencias(): Observable<OcorrenciaResponseDTO[]> {
    return this.http.get<OcorrenciaResponseDTO[]>(`${API_BASE_URL}/gestor/ocorrencias`);
  }

  listarDespachos(): Observable<DespachoResponseDTO[]> {
    return this.http.get<DespachoResponseDTO[]>(`${API_BASE_URL}/gestor/despachos`);
  }

  editarFuncionario(id: number, dto: EdicaoFuncionarioDTO): Observable<FuncionarioResponseDTO> {
    return this.http.put<FuncionarioResponseDTO>(`${API_BASE_URL}/gestor/funcionarios/${id}`, dto);
  }

  editarAmbulancia(id: number, dto: EdicaoAmbulanciaDTO): Observable<AmbulanciaResponseDTO> {
    return this.http.put<AmbulanciaResponseDTO>(`${API_BASE_URL}/gestor/ambulancias/${id}`, dto);
  }

  alterarStatusEquipe(id: number): Observable<EquipeResponseDTO> {
    return this.http.put<EquipeResponseDTO>(`${API_BASE_URL}/gestor/equipes/${id}`, {});
  }

  cadastrarEquipe(dto: RegistroEquipeDTO): Observable<EquipeResponseDTO> {
    return this.http.post<EquipeResponseDTO>(`${API_BASE_URL}/gestor/equipes`, dto);
  }
}
