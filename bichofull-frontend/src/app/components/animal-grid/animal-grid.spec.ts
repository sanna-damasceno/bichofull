import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnimalGridComponent } from './animal-grid';

describe('AnimalGrid', () => {
  let component: AnimalGridComponent;
  let fixture: ComponentFixture<AnimalGridComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AnimalGridComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(AnimalGridComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
