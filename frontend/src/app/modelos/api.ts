export type TipoFuncionario = 'CONDUTOR' | 'ENFERMEIRO' | 'MEDICO' | 'GESTOR' | 'ATENDENTE';

export type PerfilAplicacao = 'GESTOR' | 'ATENDENTE' | 'PROFISSIONAL_DA_SAUDE';

export interface AutenticacaoPayload {
  CPF: string;
  senha: string;
}

export interface TokenResponseDTO {
  token: string;
  tipoFuncionario: TipoFuncionario;
}

export interface FuncionarioResponseDTO {
  id: number;
  nome: string;
  CPF: string;
  email: string;
  tipoFuncionario: TipoFuncionario;
  ativo: boolean;
  statusTurno: string;
  equipeAtivaId: number | null;
  equipeIds: number[];
}

export interface EquipeResponseDTO {
  id: number;
  status: 'Disponível' | 'Em Atendimento' | 'Inativa';
  ambulanciaId: number | null;
  ambulanciaInfo?: string;
  funcionarioIds: number[];
  nomesFuncionarios?: string[];
}

export interface AmbulanciaResponseDTO {
  id: number;
  modelo: string;
  placa: string;
  status: 'Livre' | 'Atribuída' | 'Em Atendimento' | 'Indisponível';
}

export interface SolicitanteResponseDTO {
  nome: string;
  CPF: string;
}

export interface RegistroSolicitanteDTO {
  nome: string;
  CPF: string;
  telefone: string;
  anonimo: boolean;
}

export interface PacienteResponseDTO {
  nome: string;
  CPF: string;
}

export interface RegistroPacienteDTO {
  nome: string;
  CPF: string;
  observacoes: string;
  anonimo: boolean;
}

export interface OcorrenciaResponseDTO {
  id: number;
  dataHoraAbertura: string;
  dataHoraEncerramento: string | null;
  endereco: string;
  bairro: string;
  gravidade: 'ALTA' | 'MEDIA' | 'BAIXA';
  status: 'ABERTA' | 'EM_ATENDIMENTO' | 'CONCLUIDA';
  solicitante: SolicitanteResponseDTO;
  pacientes: RegistroPacienteDTO[];
  descricao: string;
}

export interface RegistroOcorrenciaDTO {
  endereco: string;
  bairro: string;
  gravidade: 'ALTA' | 'MEDIA' | 'BAIXA';
  descricao: string;
  solicitante: RegistroSolicitanteDTO;
  pacientes: RegistroPacienteDTO[];
}

export interface RegistroDespachoDTO {
  ocorrenciaId: number;
  equipeId: number;
  tipoDespacho: string;
}

export interface RegistroFuncionarioDTO {
  nome: string;
  CPF: string;
  email: string;
  senha?: string;
  tipoFuncionario: TipoFuncionario;
}

export interface RegistroAmbulanciaDTO {
  placa: string;
  modelo: 'Unidade de Suporte Avançado' | 'Unidade de Suporte Básico';
}

export interface RegistroEquipeDTO {
  ambulanciaId: number;
  funcionarioIds: number[];
}

export interface EdicaoFuncionarioDTO {
  nome?: string;
  CPF?: string;
  email?: string;
  ativo?: boolean;
}

export interface EdicaoAmbulanciaDTO {
  status?: string;
}

export interface RegistroFinalizacaoDespachoDTO {
  relatorioFinalizacao: string;
}

export interface DespachoResponseDTO {
  id: number;
  dataHoraDespacho: string;
  dataHoraChegadaLocal: string | null;
  dataHoraFinalizacao: string | null;
  tipoDespacho: string;
  relatorioFinalizacao: string | null;
  ocorrencia: OcorrenciaResponseDTO;
  equipe: EquipeResponseDTO;
  ambulancia: AmbulanciaResponseDTO;
}

export function perfilAplicacaoDoFuncionario(tipoFuncionario: TipoFuncionario): PerfilAplicacao {
  if (tipoFuncionario === 'GESTOR') {
    return 'GESTOR';
  }

  if (tipoFuncionario === 'ATENDENTE') {
    return 'ATENDENTE';
  }

  return 'PROFISSIONAL_DA_SAUDE';
}

export function rotaDoPerfilAplicacao(perfil: PerfilAplicacao): string {
  if (perfil === 'GESTOR') {
    return '/gestor/dashboard';
  }

  if (perfil === 'ATENDENTE') {
    return '/atendente/ocorrencias';
  }

  return '/profissional/ocorrencias';
}
