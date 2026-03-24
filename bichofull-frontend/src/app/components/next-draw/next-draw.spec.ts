import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NextDrawComponent } from './next-draw';

describe('NextDraw', () => {
  let component: NextDrawComponent;
  let fixture: ComponentFixture<NextDrawComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NextDrawComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(NextDrawComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
