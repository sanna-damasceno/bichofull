import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BetFormComponent } from './bet-form';

describe('BetForm', () => {
  let component: BetFormComponent;
  let fixture: ComponentFixture<BetFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BetFormComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(BetFormComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
