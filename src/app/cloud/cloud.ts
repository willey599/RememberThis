import { Component, signal, inject } from '@angular/core';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { MatButton } from '@angular/material/button';
import { CloudCreateMenu } from './cloud-create-menu/cloud-create-menu';
import { MatDialogModule } from '@angular/material/dialog';
import { MatDialog } from '@angular/material/dialog';



@Component({
  selector: 'app-cloud',
  imports: [DragDropModule, MatDialogModule, CloudCreateMenu],
  templateUrl: './cloud.html',
  styleUrl: './cloud.css'
})
export class Cloud {
  title = 'angular-dialog'
  mouseDownX : Number | null = null;
  mouseDownY : Number | null = null;
  mouseUpX : Number | null = null;
  mouseUpY : Number | null = null;

  //dependency injection
  readonly dialog = inject(MatDialog);

  onMouseDown(mouseState: MouseEvent): void{
    this.mouseDownX = mouseState.clientX;
    this.mouseDownY = mouseState.clientY;
  }

  //if mouseUp is in the same location as mouseDown, then dialog box should open
  onMouseUp(mouseState : MouseEvent): void {
    this.mouseUpX = mouseState.clientX;
    this.mouseUpY = mouseState.clientY;
    if (this.mouseDownX == this.mouseUpX && this.mouseDownY == this.mouseUpY){
      this.dialog.open(CloudCreateMenu);
    }
  }

}