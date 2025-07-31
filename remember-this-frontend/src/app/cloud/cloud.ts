import { Component, inject, model, Input, SimpleChanges, OnChanges, Signal, signal, WritableSignal } from '@angular/core';
import { CdkDrag, CdkDragEnd, CdkDragStart, DragDropModule } from '@angular/cdk/drag-drop';
import { MatButton } from '@angular/material/button';
import { CloudCreateMenu } from './cloud-create-menu/cloud-create-menu';
import { MAT_DIALOG_DATA, MatDialogModule } from '@angular/material/dialog';
import { MatDialog } from '@angular/material/dialog';
import { CloudService } from '../cloud-service/cloud.service';
import { P } from '@angular/cdk/keycodes';

export interface CloudData{
  nodeId : WritableSignal<number>;
  nodeText : WritableSignal<string>;
  nodeXPosition : WritableSignal<number>;
  nodeYPosition : WritableSignal<number>;
}

@Component({
  standalone: true,
  selector: 'app-cloud',
  imports: [DragDropModule, MatDialogModule, CloudCreateMenu, CdkDrag],
  template: `
    <div cdkDrag (cdkDragEnded)="onDragEnd($event)" (mouseup)="onMouseUp($event)" (mousedown)="onMouseDown($event)">
      <img src="cloud.png" alt="a blue cloud" style="cursor: pointer;">
      <h1 class="display-text">{{nodeText()}}</h1>
    </div>`,
  styles: [`
    :host {
      display: block;
      position: absolute;
      text-align: center;
      width: auto;
      height: auto;
      z-index: 2;
      }
    .display-text {
      position: absolute;
      display: block;
      top: 0%;
      left: 40%;
      color: blue;
      text-align: center;
      z-index: 1;
    }
    `]
})

export class Cloud implements CloudData{


  constructor(private cloudService: CloudService){}
  mouseDownX : number | null = null;
  mouseDownY : number | null = null;
  mouseUpX : number | null = null;
  mouseUpY : number | null = null;
  @Input() nodeId! : WritableSignal<number>;
  @Input() nodeXPosition! : WritableSignal<number>;
  @Input() nodeYPosition! : WritableSignal<number>
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
    
    if (this.mouseDownX === this.mouseUpX && this.mouseDownY === this.mouseUpY){
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
              console.log("cloud save complete");
              this.nodeText.set(result.recallItem);
            }
            catch(error: unknown){  
              console.error("An error occured when trying to save cloud data. Error: ", error);
            }
        }
      });
    }
  }


  onDragEnd($event: CdkDragEnd): void{
    //grab HTML element of this cloud inside DOM
    const cloudElement = $event.source.element.nativeElement;
    //grab HTML element of the surrounding client
    const clientRectangle = cloudElement.getBoundingClientRect();
    //x is distance from the left edge of viewport
    this.nodeXPosition.set(clientRectangle.x);
    //y is distance from top edge of viewport
    this.nodeYPosition.set(clientRectangle.y);

    console.log("position signal data: ", this.nodeXPosition(), this.nodeYPosition());
    this.cloudService.savePosition(this.nodeXPosition(), this.nodeYPosition(), this.nodeId());
    }
  }
