import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminDrawComponent } from './admin-draw';

describe('AdminDraw', () => {
  let component: AdminDrawComponent;
  let fixture: ComponentFixture<AdminDrawComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminDrawComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(AdminDrawComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
