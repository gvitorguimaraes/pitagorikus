import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeExternal } from './home-external';

describe('HomeExternal', () => {
  let component: HomeExternal;
  let fixture: ComponentFixture<HomeExternal>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HomeExternal]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HomeExternal);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
