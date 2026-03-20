import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DrawHistory } from './draw-history';

describe('DrawHistory', () => {
  let component: DrawHistory;
  let fixture: ComponentFixture<DrawHistory>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DrawHistory],
    }).compileComponents();

    fixture = TestBed.createComponent(DrawHistory);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
