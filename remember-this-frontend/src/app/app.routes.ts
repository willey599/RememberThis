import { Routes } from '@angular/router';
import { Home } from './home/home'; // You'll create this
import { Dashboard } from './dashboard/dashboard'; // You'll create this

export const routes: Routes = [
  { path: '', component: Home }, // The root path '/'
  { path: 'dashboard', component: Dashboard }, // The authenticated dashboard path
  // You can add more routes here for other features, e.g.,
  // { path: 'notes/:id', component: NoteDetailComponent },
  // { path: '**', redirectTo: '' } // Wildcard route for any unmatched paths (optional)
];