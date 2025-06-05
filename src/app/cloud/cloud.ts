import { Component } from '@angular/core';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { DialogModule } from '@angular/cdk/dialog';
import { CloudCreateMenu } from './cloud-create-menu/cloud-create-menu';
import { MatDialogModule } from '@angular/material/dialog';



@Component({
  selector: 'app-cloud',
  imports: [DragDropModule, DialogModule, MatDialogModule, CloudCreateMenu],
  templateUrl: './cloud.html',
  styleUrl: './cloud.css'
})
export class Cloud {
  
}

export interface DialogData {
  name: string;
}