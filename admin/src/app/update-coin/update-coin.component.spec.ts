import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateCoinComponent } from './update-coin.component';

describe('UpdateCoinComponent', () => {
  let component: UpdateCoinComponent;
  let fixture: ComponentFixture<UpdateCoinComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UpdateCoinComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdateCoinComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
