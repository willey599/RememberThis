import { bootstrapApplication } from '@angular/platform-browser';
import { App } from './app/app';
import { provideRouter } from '@angular/router';
import { routes } from './app/app.routes';
import { HTTP_INTERCEPTORS, provideHttpClient, withFetch, withInterceptors, withInterceptorsFromDi } from '@angular/common/http';
import { CsrfInterceptor } from './csrf-interceptor';
//lets me use httpclientmodule

bootstrapApplication(App, {
    providers: [
        provideRouter(routes), provideHttpClient(withFetch(), withInterceptorsFromDi()),{provide: HTTP_INTERCEPTORS, useClass: CsrfInterceptor, multi: true}
    ]
}).catch(err => console.error(err));