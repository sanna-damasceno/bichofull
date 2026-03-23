import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DrawsComponent } from './draws';

describe('Draws', () => {
  let component: DrawsComponent;
  let fixture: ComponentFixture<DrawsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DrawsComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(DrawsComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
