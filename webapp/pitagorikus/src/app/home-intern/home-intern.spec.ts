import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeIntern } from './home-intern';

describe('HomeIntern', () => {
  let component: HomeIntern;
  let fixture: ComponentFixture<HomeIntern>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HomeIntern]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HomeIntern);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
