import { Component, inject, model, Input, SimpleChanges, OnChanges, Signal, signal, WritableSignal } from '@angular/core';
import { CdkDrag, CdkDragEnd, CdkDragStart, DragDropModule } from '@angular/cdk/drag-drop';
import { MatButton } from '@angular/material/button';
import { CloudCreateMenu } from './cloud-create-menu/cloud-create-menu';
import { MAT_DIALOG_DATA, MatDialogModule } from '@angular/material/dialog';
import { MatDialog } from '@angular/material/dialog';
import { CloudService } from '../cloud-service/cloud.service';
import { P } from '@angular/cdk/keycodes';
import { CommonModule } from '@angular/common';

export interface CloudData{
  nodeId : WritableSignal<number>;
  nodeText : WritableSignal<string>;
  nodeContext1 : WritableSignal<string>;
  nodeContext2 : WritableSignal<string>;
  nodeContext3 : WritableSignal<string>;
  nodeXPosition : number;
  nodeYPosition : number;
}

@Component({
  standalone: true,
  selector: 'app-cloud',
  imports: [DragDropModule, MatDialogModule, CloudCreateMenu, CdkDrag, CommonModule],
  template: `
    <div cdkDrag (cdkDragEnded)="onDragEnd($event)" (mouseup)="onMouseUp($event)" (mousedown)="onMouseDown($event)">
      <img src="cloud.png" alt="a blue cloud" style="cursor: pointer;">
      <h1 class="display-text" *ngIf="displayText">{{nodeText()}}</h1>
      <h2 class="display-context1" *ngIf="displayContext">{{nodeContext1()}}</h2>
      <h2 class="display-context2" *ngIf="displayContext">{{nodeContext2()}}</h2>
      <h2 class="display-context3" *ngIf="displayContext">{{nodeContext3()}}</h2>
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
      font-size: 48px;
      display: block;
      top: 0%;
      left: 40%;
      color: blue;
      text-align: center;
      z-index: 1;
    }
    .display-context1 {
      position: absolute;
      font-size: 24px;
      display: block;
      top: -60px;
      left: 0;
      color: black;
      text-align: center;
      z-index: 1;
    }
    .display-context2 {
      position: absolute;
      font-size: 24px;
      display: block;
      top: -60px;
      left: 345px;
      color: black;
      text-align: center;
      z-index: 1;
    }
    .display-context3 {
      position: absolute;
      font-size: 24px;
      display: block;
      top: 250px;
      left: 45%;
      color: black;
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
  displayContext : boolean = true;
  displayText : boolean = false;
 
  @Input() nodeId! : WritableSignal<number>;
  @Input() nodeText! : WritableSignal<string>;
  @Input() nodeContext1!: WritableSignal<string>;
  @Input() nodeContext2!: WritableSignal<string>;
  @Input() nodeContext3!: WritableSignal<string>;
  @Input() nodeXPosition! : number;
  @Input() nodeYPosition! : number;
  

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
      const dialogRef = this.dialog.open(CloudCreateMenu, {
        data: {
          nodeText: this.nodeText(),
          nodeContext1: this.nodeContext1(),
          nodeContext2: this.nodeContext2(),
          nodeContext3: this.nodeContext3()
        }
      
      });
      
      dialogRef.afterClosed().subscribe(result => {
        console.log("entering dialog close, result", result);
        if (result == true){
          console.log("delete button clicked, calling delete service");
          this.cloudService.deleteCloud(this.nodeId());
          return;
        }
        else {
            try{
              this.cloudService.saveCloud(result.recallArray, this.nodeId());
              console.log("cloud save complete");
              //setting these values can probably offset in a service. ###TODO
              this.nodeText.set(result.recallArray[0]);
              this.nodeContext1.set(result.recallArray[1]);
              this.nodeContext2.set(result.recallArray[2]);
              this.nodeContext3.set(result.recallArray[3]);
            }
            catch(error: unknown){  
              console.error("An error occured when trying to save cloud data. Error: ", error);
            }
        }
      });
    }
  }

  onDragEnd($event: CdkDragEnd): void{
    let aggregateDragPositionX, aggregateDragPositionY;
    const xPos = $event.distance.x;
    //for historical reasons beyond my control, y is inverted. Top left is 0,0
    const yPos = $event.distance.y;
    aggregateDragPositionX = this.nodeXPosition + xPos;
    aggregateDragPositionY = this.nodeYPosition + yPos;

    console.log("x: ", xPos, "y: ", yPos, "aggregateDragPositionX: ", aggregateDragPositionX, "aggregateDragPositionY: ", aggregateDragPositionY);
    try{
      this.cloudService.savePosition(aggregateDragPositionX, aggregateDragPositionY, this.nodeId());
      //reassign nodeXPosition on this cloud
      this.nodeXPosition = aggregateDragPositionX;
      this.nodeYPosition = aggregateDragPositionY;
    }
    catch(error: unknown){
      console.error("An error occured when trying to save or set position of cloud node. Error: ", error);
    }
    // //grab HTML element of this cloud inside DOM
    // const cloudElement = $event.source.element.nativeElement;
    // //grab HTML element of the surrounding client
    // const clientRectangle = cloudElement.getBoundingClientRect();
    // //x is distance from the left edge of viewport
    // this.nodeXPosition.set(clientRectangle.x);
    // //y is distance from top edge of viewport
    // this.nodeYPosition.set(clientRectangle.y);

    // console.log("position signal data: ", this.nodeXPosition(), this.nodeYPosition());
    // this.cloudService.savePosition(this.nodeXPosition(), this.nodeYPosition(), this.nodeId());
    }
  }
