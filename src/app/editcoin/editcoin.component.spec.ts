import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditcoinComponent } from './editcoin.component';

describe('EditcoinComponent', () => {
  let component: EditcoinComponent;
  let fixture: ComponentFixture<EditcoinComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditcoinComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditcoinComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
