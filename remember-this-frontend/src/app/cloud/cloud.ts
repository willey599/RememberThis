import { Component, inject, model, Input, SimpleChanges, OnChanges, Signal, signal } from '@angular/core';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { MatButton } from '@angular/material/button';
import { CloudCreateMenu } from './cloud-create-menu/cloud-create-menu';
import { MAT_DIALOG_DATA, MatDialogModule } from '@angular/material/dialog';
import { MatDialog } from '@angular/material/dialog';
import { CloudService } from '../cloud-service/cloud.service';
import { P } from '@angular/cdk/keycodes';

export interface CloudData{
  nodeId : Signal<number>;
  nodeText : Signal<string>;
  xCoordinate : Signal<number>;
  yCoordinate : Signal<number>;
}

@Component({
  standalone: true,
  selector: 'app-cloud',
  imports: [DragDropModule, MatDialogModule, CloudCreateMenu],
  templateUrl: './cloud.html',
  styleUrl: './cloud.css'
})

export class Cloud implements CloudData{

  constructor(private cloudService: CloudService){}

  mouseDownX : Number | null = null;
  mouseDownY : Number | null = null;
  mouseUpX : Number | null = null;
  mouseUpY : Number | null = null;
  public userText = '';
  @Input() nodeId! : Signal<number>;
  @Input() xCoordinate! : Signal<number>;
  @Input() yCoordinate! : Signal<number>
  @Input() nodeText! : Signal<string>;
  
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
          console.log('Dialog closed, data: ', result);
          //store data in DB using fetch
          fetch("http://localhost:8080/api/save", {
            method: "POST",
            body: JSON.stringify({ 
            nodeText: result.recallItem,
            nodeId: this.nodeId(),
              
            }),
            headers: { "Content-Type": "application/json" },
            credentials: "include"     // Important to send cookies or auth info if backend expects it
          })
          .then(response => {
            if(response.ok){
              console.log("successful data transfer");
              this.userText = this.nodeText();
            }
            else {
              console.log("Server error:", response.status)  
              }
            })
          .then(data => console.log(data))
          .catch(err => console.error(err));
                  }
      });
    }
  }
}