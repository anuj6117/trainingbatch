import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewcoinComponent } from './viewcoin.component';

describe('ViewcoinComponent', () => {
  let component: ViewcoinComponent;
  let fixture: ComponentFixture<ViewcoinComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewcoinComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewcoinComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
