import { Injectable, signal, WritableSignal } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class LoginStatusService {
  loginStatus: WritableSignal<string> = signal('Not Logged In');

  setLoggedIn() {
    this.loginStatus.set('Logged In');
  }

  setLoggedOut() {
    this.loginStatus.set('Not Logged In');
  }
}
