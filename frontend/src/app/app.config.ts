import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { ApplicationConfig, provideBrowserGlobalErrorListeners } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { interceptadorAutenticacao } from './servicos/interceptador-autenticacao';

import { providePrimeNG } from 'primeng/config';
import Aura from '@primeuix/themes/aura';
import { MessageService } from 'primeng/api';

export const appConfig: ApplicationConfig = {
  providers: [
    provideBrowserGlobalErrorListeners(),
    providePrimeNG({theme: { preset: Aura}}),
    MessageService,
    provideHttpClient(withInterceptors([interceptadorAutenticacao])),
    provideRouter(routes)
  ]
};
