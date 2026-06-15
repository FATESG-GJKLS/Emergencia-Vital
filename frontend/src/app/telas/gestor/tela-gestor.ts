import { Component, ChangeDetectorRef } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';

import { FuncionarioResponseDTO, EquipeResponseDTO, AmbulanciaResponseDTO, OcorrenciaResponseDTO, DespachoResponseDTO, RegistroFuncionarioDTO, RegistroAmbulanciaDTO, TipoFuncionario, RegistroEquipeDTO, EdicaoFuncionarioDTO, EdicaoAmbulanciaDTO } from '../../modelos/api';
import { AutenticacaoService } from '../../servicos/autenticacao.service';
import { GestorService } from '../../servicos/gestor.service';

type TelaGestorSelecionada =
  | 'dashboard'
  | 'equipes'
  | 'criar-equipe'
  | 'colaboradores'
  | 'cadastrar-colaborador'
  | 'editar-colaborador'
  | 'ambulancias'
  | 'cadastrar-ambulancia'
  | 'editar-ambulancia'
  | 'ocorrencias'
  | 'detalhes-ocorrencia';

@Component({
  selector: 'app-tela-gestor',
  imports: [RouterLink, FormsModule],
  templateUrl: './tela-gestor.html',
  styleUrl: './tela-gestor.scss'
})
export class TelaGestor {
  funcionarios: FuncionarioResponseDTO[] = [];
  equipes: EquipeResponseDTO[] = [];
  ambulancias: AmbulanciaResponseDTO[] = [];
  ocorrencias: OcorrenciaResponseDTO[] = [];
  despachos: DespachoResponseDTO[] = [];
  ocorrenciaSelecionada: OcorrenciaResponseDTO | null = null;
  despachosDaOcorrenciaSelecionada: DespachoResponseDTO[] = [];
  telaAtual: TelaGestorSelecionada = 'dashboard';
  filtroOcorrencia: 'TODOS' | 'EM_ATENDIMENTO' | 'CONCLUIDA' = 'TODOS';
  termoPesquisaOcorrencia: string = '';
  filtroEquipe: 'TODAS' | 'Em Atendimento' | 'Disponível' = 'TODAS';
  filtroAmbulancia: 'TODAS' | 'Livre' | 'Indisponível' = 'TODAS';
  termoPesquisaAmbulancia: string = '';

  funcionarioEmEdicao: FuncionarioResponseDTO | null = null;
  ambulanciaEmEdicao: AmbulanciaResponseDTO | null = null;

  novoFuncionario: RegistroFuncionarioDTO = {
    nome: '',
    CPF: '',
    email: '',
    senha: '',
    tipoFuncionario: 'MEDICO'
  };

  novaAmbulancia: RegistroAmbulanciaDTO = {
    placa: '',
    modelo: 'Unidade de Suporte Básico'
  };
  private readonly telasPermitidas: TelaGestorSelecionada[] = [
    'dashboard',
    'equipes',
    'criar-equipe',
    'colaboradores',
    'cadastrar-colaborador',
    'editar-colaborador',
    'ambulancias',
    'cadastrar-ambulancia',
    'editar-ambulancia',
    'ocorrencias',
    'detalhes-ocorrencia'
  ];

  carregarDados(): void {
    this.gestorService.listarFuncionarios().subscribe((funcionarios) => {
      this.funcionarios = funcionarios;
      this.cdr.detectChanges();
    });

    this.gestorService.listarEquipes().subscribe((equipes) => {
      this.equipes = equipes;
      this.cdr.detectChanges();
    });

    this.gestorService.listarAmbulancias().subscribe((ambulancias) => {
      this.ambulancias = ambulancias;
      this.cdr.detectChanges();
    });

    this.gestorService.listarOcorrencias().subscribe((ocorrencias) => {
      this.ocorrencias = ocorrencias;
      this.cdr.detectChanges();
    });

    this.gestorService.listarDespachos().subscribe((despachos) => {
      this.despachos = despachos;
      this.cdr.detectChanges();
    });
  }

  constructor(
    private readonly rota: ActivatedRoute,
    private readonly roteador: Router,
    private readonly autenticacaoService: AutenticacaoService,
    private readonly gestorService: GestorService,
    private readonly cdr: ChangeDetectorRef
  ) {
    if (!this.autenticacaoService.possuiSessao()) {
      this.roteador.navigateByUrl('/');
      return;
    }

    this.rota.paramMap.subscribe((parametros) => {
      const tela = parametros.get('tela') as TelaGestorSelecionada | null;

      if (tela && this.telasPermitidas.includes(tela)) {
        this.telaAtual = tela;
        this.cdr.detectChanges();
        return;
      }

      this.roteador.navigateByUrl('/gestor/dashboard');
    });

    this.carregarDados();
  }

  trocarTela(tela: TelaGestorSelecionada): void {
    this.roteador.navigateByUrl(`/gestor/${tela}`);
  }

  get totalOcorrencias(): number {
    return this.ocorrencias.length;
  }

  get ocorrenciasConcluidas(): number {
    return this.ocorrencias.filter((o) => o.status === 'CONCLUIDA').length;
  }

  get ocorrenciasEmAtendimento(): number {
    return this.ocorrencias.filter((o) => o.status === 'EM_ATENDIMENTO').length;
  }

  formatarDataHora(valor: string | null | undefined): string {
    if (!valor) {
      return '-';
    }

    return valor.replace('T', ' ').slice(0, 16);
  }

  textoStatusOcorrencia(status: string): string {
    if (status === 'ABERTA') {
      return 'Aberta';
    }

    if (status === 'EM_ATENDIMENTO') {
      return 'Em atendimento';
    }

    return 'Finalizada';
  }

  abrirOcorrencia(ocorrencia: OcorrenciaResponseDTO) {
    this.ocorrenciaSelecionada = ocorrencia;
    this.despachosDaOcorrenciaSelecionada = this.despachos.filter(d => d.ocorrencia.id === ocorrencia.id);
    this.trocarTela('detalhes-ocorrencia');
  }

  get ocorrenciasFiltradas(): OcorrenciaResponseDTO[] {
    return this.ocorrencias.filter(o => {
      const matchFiltro = this.filtroOcorrencia === 'TODOS' || o.status === this.filtroOcorrencia;
      const matchPesquisa = !this.termoPesquisaOcorrencia ||
        o.id.toString().includes(this.termoPesquisaOcorrencia.toLowerCase()) ||
        o.solicitante.nome.toLowerCase().includes(this.termoPesquisaOcorrencia.toLowerCase());
      return matchFiltro && matchPesquisa;
    });
  }

  atualizarFiltroOcorrencia(filtro: 'TODOS' | 'EM_ATENDIMENTO' | 'CONCLUIDA'): void {
    this.filtroOcorrencia = filtro;
  }

  atualizarPesquisaOcorrencia(event: Event): void {
    const input = event.target as HTMLInputElement;
    this.termoPesquisaOcorrencia = input.value;
  }

  get equipesFiltradas(): EquipeResponseDTO[] {
    return this.equipes.filter(e => {
      return this.filtroEquipe === 'TODAS' || e.status === this.filtroEquipe || (this.filtroEquipe === 'Disponível' && (e.status as any) === 'DISPONIVEL') || (this.filtroEquipe === 'Em Atendimento' && (e.status as any) === 'EM_ATENDIMENTO');
    });
  }

  atualizarFiltroEquipe(filtro: 'TODAS' | 'Em Atendimento' | 'Disponível'): void {
    this.filtroEquipe = filtro;
  }

  get ambulanciasFiltradas(): AmbulanciaResponseDTO[] {
    return this.ambulancias.filter(a => {
      const status = a.status as any;
      const matchFiltro = this.filtroAmbulancia === 'TODAS' ||
        (this.filtroAmbulancia === 'Livre' ?
          (status === 'Livre' || status === 'LIVRE' || status === 'Atribuída' || status === 'ATRIBUIDA') :
          (status !== 'Livre' && status !== 'LIVRE' && status !== 'Atribuída' && status !== 'ATRIBUIDA'));
      const matchPesquisa = !this.termoPesquisaAmbulancia ||
        a.placa.toLowerCase().includes(this.termoPesquisaAmbulancia.toLowerCase()) ||
        a.modelo.toLowerCase().includes(this.termoPesquisaAmbulancia.toLowerCase());
      return matchFiltro && matchPesquisa;
    });
  }

  atualizarFiltroAmbulancia(filtro: 'TODAS' | 'Livre' | 'Indisponível'): void {
    this.filtroAmbulancia = filtro;
  }

  atualizarPesquisaAmbulancia(event: Event): void {
    const input = event.target as HTMLInputElement;
    this.termoPesquisaAmbulancia = input.value;
  }

  get totalFuncionarios(): number {
    return this.funcionarios.length;
  }

  get totalEquipes(): number {
    return this.equipes.length;
  }

  get totalAmbulancias(): number {
    return this.ambulancias.length;
  }

  get equipesDisponiveis(): number {
    return this.equipes.filter((equipe) => equipe.status === 'Disponível' || (equipe.status as any) === 'DISPONIVEL').length;
  }

  get profissionaisSaude(): number {
    return this.funcionarios.filter((funcionario) => funcionario.tipoFuncionario !== 'GESTOR' && funcionario.tipoFuncionario !== 'ATENDENTE').length;
  }

  get ambulanciasDisponiveis(): number {
    return this.ambulancias.filter((ambulancia) => ambulancia.status === 'Livre' || (ambulancia.status as any) === 'LIVRE' || ambulancia.status === 'Atribuída' || (ambulancia.status as any) === 'ATRIBUIDA').length;
  }

  get profissionaisSemEquipe(): FuncionarioResponseDTO[] {
    return this.funcionarios.filter((funcionario) => !funcionario.equipeAtivaId && funcionario.tipoFuncionario !== 'GESTOR' && funcionario.tipoFuncionario !== 'ATENDENTE');
  }

  get motoristasDisponiveis(): FuncionarioResponseDTO[] {
    return this.profissionaisSemEquipe.filter(f => f.tipoFuncionario === 'CONDUTOR');
  }

  get medicosDisponiveis(): FuncionarioResponseDTO[] {
    return this.profissionaisSemEquipe.filter(f => f.tipoFuncionario === 'MEDICO');
  }

  get enfermeirosDisponiveis(): FuncionarioResponseDTO[] {
    return this.profissionaisSemEquipe.filter(f => f.tipoFuncionario === 'ENFERMEIRO');
  }

  get ambulanciasLivres(): AmbulanciaResponseDTO[] {
    return this.ambulancias.filter(a => a.status === 'Livre' || (a.status as any) === 'LIVRE');
  }

  formatarStatusEquipe(status: any): string {
    const s = String(status).toUpperCase();
    if (s === 'DISPONIVEL' || s === 'DISPONÍVEL') return 'Disponível';
    if (s === 'EM_ATENDIMENTO' || s === 'EM ATENDIMENTO') return 'Em atendimento';
    if (s === 'INATIVA') return 'Inativa';
    return status;
  }

  formatarStatusAmbulancia(status: 'Livre' | 'Atribuída' | 'Em Atendimento' | 'Indisponível'): string {
    if (status === 'Livre') {
      return 'Livre';
    }

    if (status === 'Atribuída') {
      return 'Atribuída';
    }

    if (status === 'Em Atendimento') {
      return 'Em atendimento';
    }

    return 'Indisponível';
  }

  alterarStatusEquipe(id: number): void {
    this.gestorService.alterarStatusEquipe(id).subscribe({
      next: (equipeAtualizada) => {
        const index = this.equipes.findIndex(e => e.id === equipeAtualizada.id);
        if (index !== -1) {
          this.equipes[index] = equipeAtualizada;
        }

        // Recarregar funcionários e ambulâncias pois o status deles pode ter mudado (ex: ficaram livres)
        this.carregarDados();

        alert(`Status da equipe ${id} alterado para ${equipeAtualizada.status}.`);
        this.cdr.detectChanges();
      },
      error: (err) => {
        alert('Erro ao alterar status da equipe: ' + (err.error?.message || err.message));
        this.cdr.detectChanges();
      }
    });
  }

  formatarCPF(event: any, alvo: 'funcionario' | 'edicao'): void {
    let v = event.target.value.replace(/\D/g, '');
    if (v.length > 11) v = v.substring(0, 11);
    if (v.length > 9) v = v.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/, "$1.$2.$3-$4");
    else if (v.length > 6) v = v.replace(/(\d{3})(\d{3})(\d{3})/, "$1.$2.$3");
    else if (v.length > 3) v = v.replace(/(\d{3})(\d{3})/, "$1.$2");

    if (alvo === 'funcionario') this.novoFuncionario.CPF = v;
    else if (alvo === 'edicao' && this.funcionarioEmEdicao) this.funcionarioEmEdicao.CPF = v;
    event.target.value = v;
  }

  formatarPlaca(event: any): void {
    let v = event.target.value.toUpperCase().replace(/[^A-Z0-9]/g, '');
    if (v.length > 7) v = v.substring(0, 7);
    this.novaAmbulancia.placa = v;
    event.target.value = v;
  }

  validarEmail(email: string): boolean {
    const re = /^[A-Za-z][A-Za-z0-9]*([._-][A-Za-z0-9]+)*@[A-Za-z0-9]+(\.[A-Za-z]{2,})+$/;
    return re.test(email);
  }

  validarCPF(cpf: string): boolean {
    const re = /^[0-9]{3}\.[0-9]{3}\.[0-9]{3}-[0-9]{2}$/;
    return re.test(cpf);
  }

  validarPlaca(placa: string): boolean {
    const re = /^[A-Z]{3}[0-9][A-Z][0-9]{2}$/;
    return re.test(placa);
  }

  cadastrarColaborador(): void {
    if (!this.validarCPF(this.novoFuncionario.CPF)) {
      alert('CPF inválido. Use o formato 000.000.000-00');
      return;
    }
    if (!this.validarEmail(this.novoFuncionario.email)) {
      alert('Email inválido.');
      return;
    }
    if (!this.novoFuncionario.nome || !this.novoFuncionario.senha) {
      alert('Preencha todos os campos obrigatórios.');
      return;
    }

    this.gestorService.cadastrarFuncionario(this.novoFuncionario).subscribe({
      next: (res) => {
        this.funcionarios.push(res);
        alert('Colaborador cadastrado com sucesso!');
        this.novoFuncionario = { nome: '', CPF: '', email: '', senha: '', tipoFuncionario: 'MEDICO' };
        this.trocarTela('colaboradores');
        this.cdr.detectChanges();
      },
      error: (err) => {
        alert('Erro ao cadastrar colaborador: ' + (err.error?.message || err.message));
      }
    });
  }

  abrirEdicaoFuncionario(funcionario: FuncionarioResponseDTO): void {
    this.funcionarioEmEdicao = { ...funcionario };
    this.trocarTela('editar-colaborador');
  }

  editarColaborador(): void {
    if (!this.funcionarioEmEdicao) return;
    if (!this.validarCPF(this.funcionarioEmEdicao.CPF)) {
      alert('CPF inválido.');
      return;
    }
    if (!this.validarEmail(this.funcionarioEmEdicao.email)) {
      alert('Email inválido.');
      return;
    }

    const dto: EdicaoFuncionarioDTO = {
      nome: this.funcionarioEmEdicao.nome,
      CPF: this.funcionarioEmEdicao.CPF,
      email: this.funcionarioEmEdicao.email,
      ativo: this.funcionarioEmEdicao.ativo
    };

    this.gestorService.editarFuncionario(this.funcionarioEmEdicao.id, dto).subscribe({
      next: () => {
        alert('Colaborador atualizado com sucesso!');
        this.trocarTela('colaboradores');
        this.carregarDados();
      },
      error: (err) => alert('Erro ao editar: ' + (err.error?.message || err.message))
    });
  }

  cadastrarAmbulancia(): void {
    if (!this.validarPlaca(this.novaAmbulancia.placa)) {
      alert('Placa inválida. Use o formato Mercosul (ABC1D23).');
      return;
    }

    this.gestorService.cadastrarAmbulancia(this.novaAmbulancia).subscribe({
      next: (res) => {
        // O backend retorna Ambulancia (entidade), mas aqui esperamos AmbulanciaResponseDTO
        // que deve ser compatível.
        this.ambulancias.push(res as any);
        alert('Ambulância cadastrada com sucesso!');
        this.novaAmbulancia = { placa: '', modelo: 'Unidade de Suporte Básico' };
        this.trocarTela('ambulancias');
        this.cdr.detectChanges();
      },
      error: (err) => {
        alert('Erro ao cadastrar ambulância: ' + (err.error?.message || err.message));
      }
    });
  }

  abrirEdicaoAmbulancia(ambulancia: AmbulanciaResponseDTO): void {
    if (ambulancia.status === 'Atribuída' || ambulancia.status === 'Em Atendimento') {
      alert('Não é possível editar o status de uma ambulância vinculada a uma equipe ou em atendimento.');
      return;
    }
    this.ambulanciaEmEdicao = { ...ambulancia };
    this.trocarTela('editar-ambulancia');
  }

  editarAmbulancia(): void {
    if (!this.ambulanciaEmEdicao) return;
    const dto: EdicaoAmbulanciaDTO = { status: this.ambulanciaEmEdicao.status };

    this.gestorService.editarAmbulancia(this.ambulanciaEmEdicao.id, dto).subscribe({
      next: () => {
        alert('Ambulância atualizada com sucesso!');
        this.trocarTela('ambulancias');
        this.carregarDados();
      },
      error: (err) => alert('Erro ao editar: ' + (err.error?.message || err.message))
    });
  }

  cadastrarEquipe(): void {
    const motoristaNome = (document.getElementById('motorista') as HTMLSelectElement).value;
    const ambulanciaPlaca = (document.getElementById('ambulancia') as HTMLSelectElement).value;
    const medicoNome = (document.getElementById('medico') as HTMLSelectElement).value;
    const enfermeiroNome = (document.getElementById('enfermeiro') as HTMLSelectElement).value;

    if (motoristaNome === 'Selecione' || ambulanciaPlaca === 'Selecione') {
      alert('Selecione pelo menos um motorista e uma ambulância.');
      return;
    }

    const funcionarioIds: number[] = [];
    const motorista = this.motoristasDisponiveis.find(f => f.nome === motoristaNome);
    if (motorista) funcionarioIds.push(motorista.id);

    const medico = this.medicosDisponiveis.find(f => f.nome === medicoNome);
    if (medico) funcionarioIds.push(medico.id);

    const enfermeiro = this.enfermeirosDisponiveis.find(f => f.nome === enfermeiroNome);
    if (enfermeiro) funcionarioIds.push(enfermeiro.id);

    const ambulancia = this.ambulanciasLivres.find(a => a.placa === ambulanciaPlaca);

    if (!ambulancia) {
      alert('Ambulância não encontrada.');
      return;
    }

    const novaEquipe: RegistroEquipeDTO = {
      ambulanciaId: ambulancia.id,
      funcionarioIds: funcionarioIds
    };

    this.gestorService.cadastrarEquipe(novaEquipe).subscribe({
      next: (res: EquipeResponseDTO) => {
        this.equipes.push(res);
        alert('Equipe criada com sucesso!');
        this.trocarTela('equipes');
        // Recarregar listas para atualizar disponibilidade
        this.carregarDados();
        this.cdr.detectChanges();
      },
      error: (err: any) => {
        alert('Erro ao criar equipe: ' + (err.error?.message || err.message));
      }
    });
  }

  sair(): void {
    this.autenticacaoService.sair();
    this.roteador.navigateByUrl('/');
  }
}
