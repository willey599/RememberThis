import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { App } from './app/app';
import { SocialAuthServiceConfig, SocialLoginModule, GoogleLoginProvider } from '@abacritt/angularx-social-login';
//lets me use httpclientmodule
import { importProvidersFrom } from '@angular/core';

bootstrapApplication(App, appConfig)
  .catch((err) => console.error(err));
