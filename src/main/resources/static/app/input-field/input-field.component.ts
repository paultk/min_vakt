import {Component, Input, Output, DoCheck, EventEmitter} from "@angular/core";

@Component({
  moduleId: module.id,
  selector: 'input-field',
  templateUrl: './input-field.component.html',
  styleUrls: ['input-field.component.css']
})

export class InputFieldComponent implements DoCheck {
  ngDoCheck(): void {
    this.fieldValueChange.emit(this.fieldValue);
  }

  constructor(// public field: string, public fieldValue: string
  ) {
  }

  @Input()
  field: string;

  @Input()
  fieldValue: string;
  @Input()
  disabled: boolean;



  @Output()
  fieldValueChange: EventEmitter<string> = new EventEmitter<string>();

  testConnect3(): void {
    // console.log('ping3');
    // console.log(this.fieldValue);
  }

  onChanges(newValue: string): void {
    this.fieldValue = newValue;
    this.fieldValueChange.emit(newValue);
  }
}
