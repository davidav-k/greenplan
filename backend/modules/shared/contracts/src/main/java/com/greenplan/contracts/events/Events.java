package com.greenplan.contracts.events;


public sealed interface Events permits DocumentsRequested, DocumentsReady, ConceptsRequested, ConceptsReady {

}
