
create or replace procedure public.player_by_birth(
	p_player_id character,
	p_first_name character varying,
	p_last_name character varying,
	p_year_of_birth bigint,
	p_year_drafted bigint
)
language 'plpgsql'
as $BODY$
begin
insert into public.players (player_id, first_name, last_name, year_of_birth,year_drafted)
values (p_player_id,p_first_name,p_last_name,p_year_of_birth, p_year_drafted);
END;
$BODY$;