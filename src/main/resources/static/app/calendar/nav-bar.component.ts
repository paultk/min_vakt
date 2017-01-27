import {Component, Input, OnInit, Output, EventEmitter} from "@angular/core";
@Component({
moduleId: module.id,
  selector: 'nav-bar-component',
  templateUrl: 'nav-bar.component.html',
  styleUrls: [ 'nav-bar.component.css']
})
export class NavBarComponent implements OnInit{
  ngOnInit(): void {
  }
  @Input()
  headerField: Date;

  @Input()
  headerField2: string;


  @Output()
  pushed = new EventEmitter<number>();


  checkVal(number:number): void {
    this.pushed.emit(number);
  }

/*
  setYear(number: number): void {
    this.headerField.setFullYear(this.headerField.getFullYear() + number);
  }
*/


  setMonth(number: number): void {
    this.headerField.setMonth(this.headerField.getMonth() + number);
  }

  setheaderField(number: number): void {
    this.headerField.setDate(this.headerField.getDate() + number);
  }
}
















