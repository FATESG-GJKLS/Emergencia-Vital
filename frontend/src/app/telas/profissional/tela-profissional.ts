import { Component, ChangeDetectorRef } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';

import { DespachoResponseDTO } from '../../modelos/api';
import { AutenticacaoService } from '../../servicos/autenticacao.service';
import { ProfissionalService } from '../../servicos/profissional.service';

type TelaProfissionalSelecionada = 'ocorrencias' | 'detalhe-ocorrencia' | 'relatorio-atendimento';

@Component({
  selector: 'app-tela-profissional',
  standalone: true,
  imports: [RouterLink, FormsModule],
  templateUrl: './tela-profissional.html',
  styleUrl: './tela-profissional.scss'
})
export class TelaProfissional {
  despachos: DespachoResponseDTO[] = [];
  despachoSelecionado: DespachoResponseDTO | null = null;
  telaAtual: TelaProfissionalSelecionada = 'ocorrencias';
  relatorioTexto: string = '';
  private readonly telasPermitidas: TelaProfissionalSelecionada[] = ['ocorrencias', 'detalhe-ocorrencia', 'relatorio-atendimento'];

  constructor(
    private readonly rota: ActivatedRoute,
    private readonly roteador: Router,
    private readonly autenticacaoService: AutenticacaoService,
    private readonly profissionalService: ProfissionalService,
    private readonly cdr: ChangeDetectorRef
  ) {
    if (!this.autenticacaoService.possuiSessao()) {
      this.roteador.navigateByUrl('/');
      return;
    }

    this.rota.paramMap.subscribe((parametros) => {
      const tela = parametros.get('tela') as TelaProfissionalSelecionada | null;

      if (tela && this.telasPermitidas.includes(tela)) {
        this.telaAtual = tela;
        this.cdr.detectChanges();
        return;
      }

      this.roteador.navigateByUrl('/profissional/ocorrencias');
    });

    this.carregarDespachos();
  }

  carregarDespachos(): void {
    this.profissionalService.listarMeusDespachos().subscribe((despachos) => {
      this.despachos = despachos.sort((a, b) => {
        return new Date(b.dataHoraDespacho).getTime() - new Date(a.dataHoraDespacho).getTime();
      });
      this.cdr.detectChanges();
    });
  }

  trocarTela(tela: TelaProfissionalSelecionada): void {
    this.roteador.navigateByUrl(`/profissional/${tela}`);
  }

  abrirDespacho(despacho: DespachoResponseDTO): void {
    this.despachoSelecionado = despacho;
    this.relatorioTexto = despacho.relatorioFinalizacao || '';
    this.trocarTela('detalhe-ocorrencia');
  }

  informarChegada(despacho: DespachoResponseDTO): void {
    this.profissionalService.informarChegada(despacho.id).subscribe({
      next: (despachoAtualizado) => {
        const index = this.despachos.findIndex(d => d.id === despachoAtualizado.id);
        if (index !== -1) {
          this.despachos[index] = despachoAtualizado;
        }
        if (this.despachoSelecionado?.id === despachoAtualizado.id) {
          this.despachoSelecionado = despachoAtualizado;
        }
        alert('Chegada ao local informada com sucesso!');
        this.cdr.detectChanges();
      },
      error: (err) => {
        alert('Erro ao informar chegada: ' + (err.error?.message || err.message));
      }
    });
  }

  prepararFinalizacao(): void {
    this.trocarTela('relatorio-atendimento');
  }

  finalizarRelatorio(): void {
    if (!this.despachoSelecionado) return;

    this.profissionalService.finalizarDespacho(this.despachoSelecionado.id, {
      relatorioFinalizacao: this.relatorioTexto
    }).subscribe({
      next: (despachoAtualizado) => {
        const index = this.despachos.findIndex(d => d.id === despachoAtualizado.id);
        if (index !== -1) {
          this.despachos[index] = despachoAtualizado;
        }
        alert('Relatório enviado e despacho finalizado com sucesso!');
        this.trocarTela('ocorrencias');
        this.cdr.detectChanges();
      },
      error: (err) => {
        alert('Erro ao finalizar despacho: ' + (err.error?.message || err.message));
      }
    });
  }

  get totalDespachos(): number {
    return this.despachos.length;
  }

  get despachosAbertos(): number {
    return this.despachos.filter((despacho) => !despacho.dataHoraFinalizacao).length;
  }

  get despachosFinalizados(): number {
    return this.despachos.filter((despacho) => !!despacho.dataHoraFinalizacao).length;
  }

  get primeiroDespacho(): DespachoResponseDTO | null {
    return this.despachos[0] ?? null;
  }

  formatarDataHora(valor: string | null | undefined): string {
    if (!valor) {
      return '-';
    }

    return valor.replace('T', ' ').slice(0, 16);
  }

  textoStatusDespacho(despacho: DespachoResponseDTO): string {
    if (despacho.dataHoraFinalizacao) {
      return 'Finalizado';
    }

    if (despacho.dataHoraChegadaLocal) {
      return 'Em atendimento';
    }

    return 'Despachado';
  }

  sair(): void {
    this.autenticacaoService.sair();
    this.roteador.navigateByUrl('/');
  }
}
