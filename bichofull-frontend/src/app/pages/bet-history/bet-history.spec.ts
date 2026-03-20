import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BetHistoryComponent } from './bet-history';

describe('BetHistory', () => {
  let component: BetHistoryComponent;
  let fixture: ComponentFixture<BetHistoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BetHistoryComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(BetHistoryComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
