import { Component, inject, model } from '@angular/core';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { MatButton } from '@angular/material/button';
import { CloudCreateMenu } from './cloud-create-menu/cloud-create-menu';
import { MAT_DIALOG_DATA, MatDialogModule } from '@angular/material/dialog';
import { MatDialog } from '@angular/material/dialog';



@Component({
  standalone: true,
  selector: 'app-cloud',
  imports: [DragDropModule, MatDialogModule, CloudCreateMenu],
  templateUrl: './cloud.html',
  styleUrl: './cloud.css'
})
export class Cloud {
  name : string = '';

  mouseDownX : Number | null = null;
  mouseDownY : Number | null = null;
  mouseUpX : Number | null = null;
  mouseUpY : Number | null = null;
  public userWord = '';

  //dependency injection, create handle for MatDialog object
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
      const dialogRef = this.dialog.open(CloudCreateMenu /*, {data: {name: this.name}}*/);
      
      dialogRef.afterClosed().subscribe(result => {
        if (result){
          console.log('Dialog closed, data: ', result);
          this.userWord = result;
          //store data in DB when you create the database
        }
      });
    }
  }
}