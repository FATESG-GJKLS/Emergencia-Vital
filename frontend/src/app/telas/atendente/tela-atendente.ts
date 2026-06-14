import { Component, ChangeDetectorRef } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';

import { OcorrenciaResponseDTO, DespachoResponseDTO, EquipeResponseDTO, AmbulanciaResponseDTO, RegistroOcorrenciaDTO } from '../../modelos/api';
import { AutenticacaoService } from '../../servicos/autenticacao.service';
import { AtendenteService } from '../../servicos/atendente.service';

type TelaAtendenteSelecionada = 'nova-ocorrencia' | 'nova-ocorrencia-localizacao' | 'nova-ocorrencia-despacho' | 'ocorrencias' | 'detalhes-ocorrencia' | 'reforco-despacho';

@Component({
  selector: 'app-tela-atendente',
  imports: [RouterLink, FormsModule],
  templateUrl: './tela-atendente.html',
  styleUrl: './tela-atendente.scss'
})
export class TelaAtendente {
  ocorrencias: OcorrenciaResponseDTO[] = [];
  despachos: DespachoResponseDTO[] = [];
  equipes: EquipeResponseDTO[] = [];
  ambulancias: AmbulanciaResponseDTO[] = [];
  ocorrenciaSelecionada: OcorrenciaResponseDTO | null = null;
  despachosDaOcorrenciaSelecionada: DespachoResponseDTO[] = [];
  telaAtual: TelaAtendenteSelecionada = 'ocorrencias';
  filtroOcorrencia: 'TODOS' | 'ABERTA' | 'EM_ATENDIMENTO' | 'CONCLUIDA' = 'TODOS';
  termoPesquisa: string = '';

  bairros = [
    { nome: 'CENTRO', local: 'Centro', distancia: 0.5 },
    { nome: 'OLIVEIRAS', local: 'Bairro das Oliveiras', distancia: 1.5 },
    { nome: 'APARECIDA', local: 'Vila Aparecida', distancia: 3.0 },
    { nome: 'AUGUSTO', local: 'Jardim Augusto', distancia: 4.2 },
    { nome: 'SAOJOSE', local: 'Distrito de São José', distancia: 4.8 },
    { nome: 'CANEDO', local: 'Alto do Canedo', distancia: 5.5 },
    { nome: 'ANTONIO', local: 'Vale Santo Antônio', distancia: 7.0 },
    { nome: 'AZUL', local: 'Setor Serra Azul', distancia: 12.0 },
    { nome: 'UNIVERSITARIO', local: 'Assentamento Universitário', distancia: 16.5 },
    { nome: 'NOROESTE', local: 'Parque Noroeste', distancia: 22.0 }
  ];

  novaOcorrencia = {
    solicitante: { nome: '', CPF: '', telefone: '', anonimo: false },
    pacientes: [{ nome: '', CPF: '', observacoes: '', anonimo: false }],
    endereco: '',
    bairro: 'CENTRO',
    gravidade: 'MEDIA',
    descricao: ''
  };

  private readonly telasPermitidas: TelaAtendenteSelecionada[] = ['nova-ocorrencia', 'nova-ocorrencia-localizacao', 'nova-ocorrencia-despacho', 'ocorrencias', 'detalhes-ocorrencia', 'reforco-despacho'];

  constructor(
    private readonly rota: ActivatedRoute,
    private readonly roteador: Router,
    private readonly autenticacaoService: AutenticacaoService,
    private readonly atendenteService: AtendenteService,
    private readonly cdr: ChangeDetectorRef
  ) {
    if (!this.autenticacaoService.possuiSessao()) {
      this.roteador.navigateByUrl('/');
      return;
    }

    this.rota.paramMap.subscribe((parametros) => {
      const tela = parametros.get('tela') as TelaAtendenteSelecionada | null;

      if (tela && this.telasPermitidas.includes(tela)) {
        this.telaAtual = tela;
        this.cdr.detectChanges();
        return;
      }

      this.roteador.navigateByUrl('/atendente/ocorrencias');
    });

    this.atendenteService.listarOcorrencias().subscribe((ocorrencias) => {
      this.ocorrencias = ocorrencias;
      this.cdr.detectChanges();
    });

    this.atendenteService.listarDespachos().subscribe((despachos) => {
      this.despachos = despachos;
      this.cdr.detectChanges();
    });

    this.atendenteService.listarEquipes().subscribe((equipes) => {
      this.equipes = equipes;
      this.cdr.detectChanges();
    });

    this.atendenteService.listarAmbulancias().subscribe((ambulancias) => {
      this.ambulancias = ambulancias;
      this.cdr.detectChanges();
    });
  }

  formatarCPF(event: any): void {
    let v = event.target.value.replace(/\D/g, '');
    if (v.length > 11) v = v.substring(0, 11);
    if (v.length > 9) v = v.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/, "$1.$2.$3-$4");
    else if (v.length > 6) v = v.replace(/(\d{3})(\d{3})(\d{3})/, "$1.$2.$3");
    else if (v.length > 3) v = v.replace(/(\d{3})(\d{3})/, "$1.$2");
    event.target.value = v;
  }

  formatarTelefone(event: any): void {
    let v = event.target.value.replace(/\D/g, '');
    if (v.length > 11) v = v.substring(0, 11);

    // Formato: (NN)9-NNNNNNNN
    if (v.length > 10) v = v.replace(/(\d{2})(\d{1})(\d{8})/, "($1)$2-$3");
    else if (v.length > 3) v = v.replace(/(\d{2})(\d{1})(\d{0,8})/, "($1)$2-$3");
    else if (v.length > 2) v = v.replace(/(\d{2})(\d{0,1})/, "($1)$2");

    event.target.value = v;
  }

  adicionarPaciente(): void {
    this.novaOcorrencia.pacientes.push({ nome: '', CPF: '', observacoes: '', anonimo: false });
  }

  removerPaciente(index: number): void {
    if (this.novaOcorrencia.pacientes.length > 1) {
      this.novaOcorrencia.pacientes.splice(index, 1);
    }
  }

  get equipesDisponiveisParaDespacho(): EquipeResponseDTO[] {
    return this.equipes.filter(e => e.status === 'Disponível' || (e.status as any) === 'DISPONIVEL');
  }

  criarOcorrencia(): void {
    // Limpar máscaras e tratar campos anônimos antes de enviar
    const dadosParaEnviar: RegistroOcorrenciaDTO = JSON.parse(JSON.stringify(this.novaOcorrencia));

    // Tratar solicitante anônimo
    if (dadosParaEnviar.solicitante.anonimo) {
      dadosParaEnviar.solicitante.nome = 'Anônimo';
      dadosParaEnviar.solicitante.CPF = null as any;
      dadosParaEnviar.solicitante.telefone = null as any;
    }

    // Tratar pacientes anônimos
    dadosParaEnviar.pacientes.forEach(p => {
      if (p.anonimo) {
        p.nome = 'Anônimo';
        p.CPF = null as any;
      }
    });

    this.atendenteService.registrarOcorrencia(dadosParaEnviar).subscribe({
      next: (ocorrencia: OcorrenciaResponseDTO) => {
        this.ocorrencias.push(ocorrencia);
        this.ocorrenciaSelecionada = ocorrencia;
        this.trocarTela('nova-ocorrencia-despacho');
        this.cdr.detectChanges();
      },
      error: (err: any) => {
        alert('Erro ao criar ocorrência: ' + (err.error?.message || err.message));
        this.cdr.detectChanges();
      }
    });
  }

  enviarDespacho(equipeId: number): void {
    if (!this.ocorrenciaSelecionada) return;

    const temDespacho = this.despachos.some(d => d.ocorrencia.id === this.ocorrenciaSelecionada!.id);
    const tipoDespacho = temDespacho ? 'REFORCO' : 'INICIAL';

    this.atendenteService.enviarDespacho({
      ocorrenciaId: this.ocorrenciaSelecionada.id,
      equipeId: equipeId,
      tipoDespacho: tipoDespacho
    }).subscribe({
      next: (despacho: DespachoResponseDTO) => {
        this.despachos.push(despacho);

        // Recarregar dados para garantir que os status (equipe, ambulância, ocorrência) estejam atualizados
        this.atendenteService.listarEquipes().subscribe(equipes => {
          this.equipes = equipes;
          this.cdr.detectChanges();
        });
        this.atendenteService.listarAmbulancias().subscribe(ambulancias => {
          this.ambulancias = ambulancias;
          this.cdr.detectChanges();
        });
        this.atendenteService.listarOcorrencias().subscribe(ocorrencias => {
          this.ocorrencias = ocorrencias;
          this.cdr.detectChanges();
        });

        alert('Despacho enviado com sucesso!');
        if (this.telaAtual === 'reforco-despacho') {
          this.abrirOcorrencia(this.ocorrenciaSelecionada!);
        } else {
          this.trocarTela('ocorrencias');
        }
        this.cdr.detectChanges();
      },
      error: (err: any) => {
        alert('Erro ao enviar despacho: ' + (err.error?.message || err.message));
        this.cdr.detectChanges();
      }
    });
  }

  trocarTela(tela: TelaAtendenteSelecionada): void {
    this.roteador.navigateByUrl(`/atendente/${tela}`);
  }

  get totalOcorrencias(): number {
    return this.ocorrencias.length;
  }

  get totalDespachos(): number {
    return this.ocorrencias.filter(o => o.status === 'EM_ATENDIMENTO').length;
  }

  get ocorrenciasFiltradas(): OcorrenciaResponseDTO[] {
    return this.ocorrencias.filter(o => {
      const matchFiltro = this.filtroOcorrencia === 'TODOS' || o.status === this.filtroOcorrencia;
      const matchPesquisa = !this.termoPesquisa ||
        o.id.toString().includes(this.termoPesquisa.toLowerCase()) ||
        o.solicitante.nome.toLowerCase().includes(this.termoPesquisa.toLowerCase()) ||
        o.descricao.toLowerCase().includes(this.termoPesquisa.toLowerCase());
      return matchFiltro && matchPesquisa;
    });
  }

  atualizarFiltro(filtro: 'TODOS' | 'ABERTA' | 'EM_ATENDIMENTO' | 'CONCLUIDA'): void {
    this.filtroOcorrencia = filtro;
  }

  atualizarPesquisa(event: Event): void {
    const input = event.target as HTMLInputElement;
    this.termoPesquisa = input.value;
  }

  get ocorrenciasConcluidas(): number {
    return this.ocorrencias.filter((ocorrencia) => ocorrencia.status === 'CONCLUIDA').length;
  }

  formatarDataHora(valor: string | null | undefined): string {
    if (!valor) {
      return '-';
    }

    return valor.replace('T', ' ').slice(0, 16);
  }

  classeGravidade(gravidade: string): string {
    if (gravidade === 'ALTA') {
      return 'Alta';
    }

    if (gravidade === 'MEDIA') {
      return 'Média';
    }

    return 'Baixa';
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

  classeStatusOcorrencia(status: string): string {
    if (status === 'ABERTA') {
      return 'despachada';
    }

    if (status === 'EM_ATENDIMENTO') {
      return 'atendimento';
    }

    return 'finalizado';
  }

  formatarStatusEquipe(status: any): string {
    const s = String(status).toUpperCase();
    if (s === 'DISPONIVEL' || s === 'DISPONÍVEL') return 'Disponível';
    if (s === 'EM_ATENDIMENTO' || s === 'EM ATENDIMENTO') return 'Em atendimento';
    if (s === 'INATIVA') return 'Inativa';
    return status;
  }

  abrirOcorrencia(ocorrencia: OcorrenciaResponseDTO) {
    this.ocorrenciaSelecionada = ocorrencia;
    this.despachosDaOcorrenciaSelecionada = this.despachos.filter(d => d.ocorrencia.id === ocorrencia.id);
    this.trocarTela('detalhes-ocorrencia');
  }

  get podeFinalizar(): boolean {
    if (!this.ocorrenciaSelecionada || this.ocorrenciaSelecionada.status === 'CONCLUIDA') {
      return false;
    }

    return this.despachosDaOcorrenciaSelecionada.every(d => d.dataHoraFinalizacao !== null);
  }

  finalizarOcorrencia(): void {
    if (!this.ocorrenciaSelecionada) return;

    this.atendenteService.encerrarOcorrencia(this.ocorrenciaSelecionada.id).subscribe({
      next: (ocorrenciaAtualizada) => {
        this.ocorrenciaSelecionada = ocorrenciaAtualizada;
        const index = this.ocorrencias.findIndex(o => o.id === ocorrenciaAtualizada.id);
        if (index !== -1) {
          this.ocorrencias[index] = ocorrenciaAtualizada;
        }
        alert('Ocorrência finalizada com sucesso!');
        this.cdr.detectChanges();
      },
      error: (err) => {
        alert('Erro ao finalizar ocorrência: ' + (err.error?.message || err.message));
        this.cdr.detectChanges();
      }
    });
  }

  sair(): void {
    this.autenticacaoService.sair();
    this.roteador.navigateByUrl('/');
  }
}
