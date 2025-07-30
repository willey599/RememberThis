import { Component, inject, model, Input, SimpleChanges, OnChanges, Signal, signal, WritableSignal } from '@angular/core';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { MatButton } from '@angular/material/button';
import { CloudCreateMenu } from './cloud-create-menu/cloud-create-menu';
import { MAT_DIALOG_DATA, MatDialogModule } from '@angular/material/dialog';
import { MatDialog } from '@angular/material/dialog';
import { CloudService } from '../cloud-service/cloud.service';
import { P } from '@angular/cdk/keycodes';

export interface CloudData{
  nodeId : WritableSignal<number>;
  nodeText : WritableSignal<string>;
  xPosition : WritableSignal<number>;
  yPosition : WritableSignal<number>;
}

@Component({
  standalone: true,
  selector: 'app-cloud',
  imports: [DragDropModule, MatDialogModule, CloudCreateMenu],
  template: `
    <div cdkDrag>
      <img src="cloud.png" alt="cloud" style="cursor: pointer;" (mousedown)="onMouseDown($event)" (mouseup)="onMouseUp($event)">
      <h1>{{nodeText()}}</h1>
    </div>`,
  styleUrl: './cloud.css'
})

export class Cloud implements CloudData{

  constructor(private cloudService: CloudService){}

  mouseDownX : Number | null = null;
  mouseDownY : Number | null = null;
  mouseUpX : Number | null = null;
  mouseUpY : Number | null = null;
  @Input() nodeId! : WritableSignal<number>;
  @Input() xPosition! : WritableSignal<number>;
  @Input() yPosition! : WritableSignal<number>
  @Input() nodeText! : WritableSignal<string>;
  
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
      //opens the dialog box, contains the result after opening
      const dialogRef = this.dialog.open(CloudCreateMenu, {data: {nodeText: this.nodeText}});
      
      dialogRef.afterClosed().subscribe(result => {
        console.log("entering dialog close, result", result);
        if (result == true){
          console.log("delete button clicked, calling delete service");
          this.cloudService.deleteCloud(this.nodeId());
          return;
        }
        else {
            try{
              this.cloudService.saveCloud(result.recallItem, this.nodeId());
              this.nodeText.set(result.recallItem);
            }
            catch(error: unknown){  
              console.error("An error occured when trying to save cloud data. Error: ", error);
            }
        }
      });
    }
  }
}