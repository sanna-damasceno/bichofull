import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NextDraw } from './next-draw';

describe('NextDraw', () => {
  let component: NextDraw;
  let fixture: ComponentFixture<NextDraw>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NextDraw],
    }).compileComponents();

    fixture = TestBed.createComponent(NextDraw);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
